package com.machinarium.utility.validators;

import java.util.Map;
import java.util.regex.Pattern;

public class MappedValidator implements Validator {

    /**
     * <p>
     * Constructs a {@link MappedValidator} from the specified map. The map <b>is not
     * copied and only the reference is stored.</b>
     * </p>
     *
     * <p>
     * The specified validator map should be a mapping of the form: <br>
     *      {Regular Expression String : Condition that the regular expression checks}
     * </p>
     *
     * <p>
     * The specified inputs will later be checked to see if they <b>fully match</b> the
     * regular expressions specified in this map. The invalidation reason will be
     * the one mapped to the first regular expression that doesn't match.
     * </p>
     *
     * @param validationMap The validator map to be used for validations.
     */
    public MappedValidator(Map<String, String> validationMap) {
        this.validationMap = validationMap;
    }

    @Override
    public String on(String input) {

        for(String regexp : validationMap.keySet()) {
            if(!Pattern.compile(regexp).matcher(input).matches()) return validationMap.get(regexp);
        }
        return null;
    };

    private final Map<String, String> validationMap;
}
