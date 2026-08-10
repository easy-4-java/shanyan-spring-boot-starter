package com.shanyan.spring.boot.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MD5}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class MD5Test {

    @Test
    void getMD5CodeShouldReturnNonNullForValidInput() {
        String result = MD5.getMD5Code("hello");
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void getMD5CodeShouldReturnConsistentResult() {
        String r1 = MD5.getMD5Code("test");
        String r2 = MD5.getMD5Code("test");
        assertThat(r1).isEqualTo(r2);
    }

    @Test
    void getMD5CodeShouldReturnDifferentResultsForDifferentInputs() {
        String r1 = MD5.getMD5Code("hello");
        String r2 = MD5.getMD5Code("world");
        assertThat(r1).isNotEqualTo(r2);
    }

    @Test
    void getMD5CodeShouldHandleEmptyString() {
        String result = MD5.getMD5Code("");
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }
}
