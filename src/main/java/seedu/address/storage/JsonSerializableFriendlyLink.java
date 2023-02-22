package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FriendlyLink;
import seedu.address.model.ReadOnlyFriendlyLink;
import seedu.address.model.pair.Pair;
import seedu.address.model.person.Person;

/**
 * An Immutable FriendlyLink that is serializable to JSON format.
 */
@JsonRootName(value = "friendlylink")
class JsonSerializableFriendlyLink {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_PAIR = "Persons list contains duplicate pair(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    private final List<JsonAdaptedPair> pairs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFriendlyLink} with the given persons.
     */
    @JsonCreator
    public JsonSerializableFriendlyLink(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("pairs") List<JsonAdaptedPair> pairs) {
        this.persons.addAll(persons);
        this.pairs.addAll(pairs);
    }

    /**
     * Converts a given {@code ReadOnlyFriendlyLink} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFriendlyLink}.
     */
    public JsonSerializableFriendlyLink(ReadOnlyFriendlyLink source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        pairs.addAll(source.getPairList().stream().map(JsonAdaptedPair::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code FriendlyLink} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FriendlyLink toModelType() throws IllegalValueException {
        FriendlyLink friendlyLink = new FriendlyLink();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (friendlyLink.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            friendlyLink.addPerson(person);
        }
        for (JsonAdaptedPair jsonAdaptedPair : pairs) {
            Pair pair = jsonAdaptedPair.toModelType(friendlyLink);
            if (friendlyLink.hasPair(pair)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PAIR);
            }
            friendlyLink.addPair(pair);
        }
        return friendlyLink;
    }

}
