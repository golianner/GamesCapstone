package com.dicoding.core.utils

import com.dicoding.core.domain.model.game.GenreModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DataHelperTest {

    @Test
    fun `invalid date return -`() {
        val result = DataHelper.formatDate("")
        assertThat(result).isEqualTo("-")
    }

    @Test
    fun `valid date return normal date pattern example 31 March 2023`() {
        val result = DataHelper.formatDate("2023-03-31")
        assertThat(result).isEqualTo("31 March 2023")
    }

    @Test
    fun `if genre is available, return list of genre that have separation by ,`() {
        val result = DataHelper.genreListing(listOf(
            GenreModel(0, "Test"), GenreModel(0, "Oke")
        ))
        assertThat(result).isEqualTo("Test, Oke")
    }

    @Test
    fun `if genre is empty, return -`() {
        val result = DataHelper.genreListing(emptyList())
        assertThat(result).isEqualTo("-")
    }

    @Test
    fun `return xx games`() {
        val result = DataHelper.gamesCount(4)
        assertThat(result).isEqualTo("4 games")
    }
}