package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Block;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Photo parsePhoto(String photo) throws ParseException {
        requireNonNull(photo);
        String trimmedPhoto = photo.trim();
        if (!Photo.isValidPhotoPath(trimmedPhoto)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Photo(trimmedPhoto);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String} into a {@code Block}.
     * @param s                 The String representation of this block.
     * @return                  The resulting block.
     * @throws ParseException   If the String cannot be parsed.
     */
    public static Block parseBlock(String s) throws ParseException {
        requireNonNull(s);
        String t = s;
        t.trim();
        boolean isAntiBlock = t.charAt(0) == '!';
        if (isAntiBlock) {
            t = t.substring(1).trim();
        }

        switch (t) {
        case Block.MORNING:
            return Block.morning(isAntiBlock);

        case Block.AFTERNOON:
            return Block.afternoon(isAntiBlock);

        case Block.EVENING:
            return Block.evening(isAntiBlock);

        case Block.NIGHT:
            return Block.night(isAntiBlock);

        case Block.MIDNIGHT:
            return Block.midnight(isAntiBlock);

        case Block.SCHOOL:
            return Block.school(isAntiBlock);

        case Block.BREAKFAST:
            return Block.breakfast(isAntiBlock);

        case Block.LUNCH:
            return Block.lunch(isAntiBlock);

        case Block.DINNER:
            return Block.dinner(isAntiBlock);

        case Block.SUPPER:
            return Block.supper(isAntiBlock);

        case Block.BRUNCH:
            return Block.brunch(isAntiBlock);

        default:
            break;

        }
        String[] times = t.split(" ");

        if (times.length != 2) {
            throw new ParseException(Block.MESSAGE_CONSTRAINTS);
        }
        LocalTime first;
        LocalTime second;
        try {
            first = LocalTime.parse(times[0]);
            second = LocalTime.parse(times[1]);
        } catch (DateTimeParseException e) {
            throw new ParseException(Block.MESSAGE_CONSTRAINTS);
        }

        return new Block(first, second, isAntiBlock);
    }

    /**
     * Parses a Collection of Strings into a Set of {@code blocks}.
     * @param blocks            The Collection of Strings that is to be parsed.
     * @return                  The resulting Set of blocks.
     * @throws ParseException   If there is an exception upon parsing any String.
     */
    public static Set<Block> parseBlocks(Collection<String> blocks) throws ParseException {
        requireNonNull(blocks);
        final Set<Block> blockSet = new HashSet<>();
        for (String s : blocks) {
            blockSet.add(parseBlock(s));
        }
        return blockSet;
    }
}
