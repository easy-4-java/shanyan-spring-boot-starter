package com.shanyan.spring.boot.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ByteFormat}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class ByteFormatTest {

    @Test
    void bytesToHexStringShouldConvertCorrectly() {
        byte[] input = {0x0A, (byte) 0xFF, 0x00, 0x7B};
        String result = ByteFormat.bytesToHexString(input);
        assertThat(result).isEqualTo("0AFF007B");
    }

    @Test
    void bytesToHexStringShouldHandleSingleByte() {
        byte[] input = {0x01};
        String result = ByteFormat.bytesToHexString(input);
        assertThat(result).isEqualTo("01");
    }

    @Test
    void hexToBytesShouldConvertCorrectly() {
        byte[] result = ByteFormat.hexToBytes("0AFF007B");
        assertThat(result).hasSize(4);
        assertThat(result[0]).isEqualTo((byte) 0x0A);
        assertThat(result[1]).isEqualTo((byte) 0xFF);
        assertThat(result[2]).isEqualTo((byte) 0x00);
        assertThat(result[3]).isEqualTo((byte) 0x7B);
    }

    @Test
    void hexToBytesShouldReturnNullForNullInput() {
        assertThat(ByteFormat.hexToBytes(null)).isNull();
    }

    @Test
    void hexToBytesShouldHandleLowercase() {
        byte[] result = ByteFormat.hexToBytes("0aff");
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo((byte) 0x0A);
        assertThat(result[1]).isEqualTo((byte) 0xFF);
    }

    @Test
    void roundTripConversion() {
        byte[] original = {1, 2, 3, 127, (byte) 128, (byte) 255};
        String hex = ByteFormat.bytesToHexString(original);
        byte[] restored = ByteFormat.hexToBytes(hex);
        assertThat(restored).isEqualTo(original);
    }

    @Test
    void constructorShouldBeInstantiable() {
        ByteFormat bf = new ByteFormat();
        assertThat(bf).isNotNull();
    }

    @Test
    void mainMethodShouldBeInvocable() {
        // Exercise the main method for coverage
        ByteFormat.main(new String[]{});
    }

    @Test
    void hexToBytesShouldHandleHighBitValues() {
        // Test bytes > 127 which trigger the value -= 256 branch
        byte[] result = ByteFormat.hexToBytes("80FF");
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo((byte) 0x80);
        assertThat(result[1]).isEqualTo((byte) 0xFF);
    }
}
