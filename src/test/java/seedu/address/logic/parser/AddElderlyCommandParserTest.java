package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AVAILABLE_DATES_ONE;
import static seedu.address.logic.commands.CommandTestUtil.AVAILABLE_DATES_TWO;
import static seedu.address.logic.commands.CommandTestUtil.BIRTH_DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTH_DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAILABLE_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTH_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REGION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RISK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REGION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REGION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.RISK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RISK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SINGLE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_STRONG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTH_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RISK_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SINGLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STRONG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalElderly.AMY;
import static seedu.address.testutil.TypicalElderly.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddElderlyCommand;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.information.AvailableDate;
import seedu.address.model.person.information.BirthDate;
import seedu.address.model.person.information.Email;
import seedu.address.model.person.information.Name;
import seedu.address.model.person.information.Nric;
import seedu.address.model.person.information.Phone;
import seedu.address.model.person.information.Region;
import seedu.address.model.person.information.RiskLevel;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ElderlyBuilder;

public class AddElderlyCommandParserTest {
    private final AddElderlyCommandParser parser = new AddElderlyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Elderly expectedElderly = new ElderlyBuilder(BOB).withTags(VALID_TAG_STRONG)
                .withAvailableDates(VALID_START_DATE_ONE, VALID_END_DATE_ONE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderly));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderly));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderly));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderly));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderly));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_AMY + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderly));

        // multiple age - last age accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_AMY + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderly));

        // multiple region - last region accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_AMY + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderly));

        // multiple risk levels - last risk level accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_AMY + RISK_DESC_BOB
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderly));

        // multiple date ranges - all accepted
        Elderly expectedElderlyMultipleDates = new ElderlyBuilder(BOB).withTags(VALID_TAG_STRONG, VALID_TAG_SINGLE)
                .withAvailableDates(VALID_START_DATE_ONE, VALID_END_DATE_ONE)
                .withAvailableDates(VALID_START_DATE_TWO, VALID_END_DATE_TWO)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB
                        + REGION_DESC_BOB + RISK_DESC_BOB + TAG_DESC_SINGLE
                        + TAG_DESC_STRONG + AVAILABLE_DATES_ONE + AVAILABLE_DATES_TWO,
                new AddElderlyCommand(expectedElderlyMultipleDates));

        // multiple tags - all accepted
        Elderly expectedElderlyMultipleTags = new ElderlyBuilder(BOB).withTags(VALID_TAG_STRONG, VALID_TAG_SINGLE)
                .withAvailableDates(VALID_START_DATE_ONE, VALID_END_DATE_ONE)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB + TAG_DESC_SINGLE
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, new AddElderlyCommand(expectedElderlyMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Elderly expectedElderly = new ElderlyBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + NRIC_DESC_AMY + BIRTH_DATE_DESC_AMY + REGION_DESC_AMY
                + RISK_DESC_AMY, new AddElderlyCommand(expectedElderly));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddElderlyCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB, expectedMessage);

        // missing nric prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_NRIC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB, expectedMessage);

        // missing birthdate prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + VALID_BIRTH_DATE_BOB + REGION_DESC_BOB + RISK_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_NRIC_BOB + VALID_BIRTH_DATE_BOB + VALID_REGION_BOB + VALID_RISK_LEVEL_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB + TAG_DESC_SINGLE
                + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_SINGLE + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_SINGLE + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, Email.MESSAGE_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_NRIC_DESC + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_SINGLE + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, Nric.MESSAGE_CONSTRAINTS);

        // invalid birth date
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + INVALID_BIRTH_DATE_DESC + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_SINGLE + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, BirthDate.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + TAG_DESC_SINGLE + TAG_DESC_STRONG + INVALID_AVAILABLE_DATE, AvailableDate.MESSAGE_CONSTRAINTS);

        // invalid region
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + INVALID_REGION_DESC + RISK_DESC_BOB
                + TAG_DESC_SINGLE + TAG_DESC_STRONG, Region.MESSAGE_CONSTRAINTS);

        // invalid risk level
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + INVALID_RISK_DESC
                + TAG_DESC_SINGLE + TAG_DESC_STRONG + AVAILABLE_DATES_ONE, RiskLevel.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB + RISK_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_STRONG, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + INVALID_REGION_DESC
                + RISK_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + NRIC_DESC_BOB + BIRTH_DATE_DESC_BOB + REGION_DESC_BOB
                + RISK_DESC_BOB + TAG_DESC_SINGLE + TAG_DESC_STRONG + AVAILABLE_DATES_ONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddElderlyCommand.MESSAGE_USAGE));
    }
}
