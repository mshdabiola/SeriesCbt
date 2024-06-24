/*
 *abiola 2024
 */

package com.mshdabiola.testing.repository

import com.mshdabiola.model.Note

class TestNoteRepository {

    private val data = MutableList(4) { index ->
        Note(index.toLong(), "title", "Content")
    }
}
