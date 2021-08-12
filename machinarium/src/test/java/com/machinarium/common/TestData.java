package com.machinarium.common;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestData implements Iterable<TestCase> {

    public static final String COLUMN_ID = "Test Case ID";

    private static final CSVParser parser = new CSVParserBuilder()
        .withSeparator(',')
        .withQuoteChar('"')
        .withEscapeChar('\t')
        .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_QUOTES)
        .withIgnoreLeadingWhiteSpace(true).build();

    /**
     * Returns a {@link TestData} object created from the .csv file at the specified path.
     *
     * @param path Path to the .csv file.
     * @return The generated test data as a {@link TestData} object.
     */
    public static TestData loadTestData(String path) {

        assert path != null: "Path to test data cannot be null.";

        if(!loadedDataMap.containsKey(path)) loadedDataMap.put(path, new TestData(path));

        return loadedDataMap.get(path);
    }

    @Override
    public Iterator<TestCase> iterator() {return this.testCases.iterator();}

    private TestData(String path) {

        this.columnNames = new ArrayList<>();
        this.testCases = new ArrayList<>();
        this.testCaseIDs = new HashMap<>();

        try {

            CSVReader csvReader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).build();
            List<String[]> rows = csvReader.readAll();

            this.columnNames.addAll(List.of(rows.get(0)));
            assert this.columnNames.contains(COLUMN_ID):
                    "The test data must contain an ID column with the name \"" + COLUMN_ID + "\".";

            for(int i = 1; i < rows.size(); i++){

                Map<String, String> parsedData = getTestCaseData(rows.get(i), i + 1);
                if(parsedData != null) testCases.add(new TestCase(parsedData));
            }

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);
        } catch (AssertionError e) {

            System.out.println("An AssertionError was raised when parsing the test data file at: " + path + ".");
            System.out.println("Error: " + e.getMessage());

            e.printStackTrace();
            System.exit(1);
        } catch (CsvException e) {

            e.printStackTrace();
        }
    }

    private Map<String, String> getTestCaseData(String[] row, int rowNumber) {

        Map<String, String> testCaseData = new HashMap<>();

        int nColumns = this.columnNames.size();

        for(int i = 0; i < nColumns; i++) {

            String currentColumn = this.columnNames.get(i);

            if(currentColumn.equals(COLUMN_ID)) {

                String testCaseID = row[i];

                assert !this.testCaseIDs.containsKey(testCaseID):
                        "Duplicate ID " + testCaseID + " found on lines " + testCaseIDs.get(testCaseID) + " and " + rowNumber + ".";

                if(testCaseID.equals("")) {
                    logger.log(Level.WARNING, "Skipping row " + rowNumber + ". Value in the ID column is not specified.");
                    return null;

                } else this.testCaseIDs.put(testCaseID, rowNumber);
            }

            testCaseData.put(currentColumn, row[i]);
        }
        return testCaseData;
    }

    private final List<String> columnNames;
    private final List<TestCase> testCases;
    private final Map<String, Integer> testCaseIDs;

    private static final Logger logger = Logger.getLogger("TestData");
    private static final Map<String, TestData> loadedDataMap = new HashMap<>();
}
