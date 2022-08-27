package com.facundocetraro.deptchallenge

import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun dataShouldBeHelloWorld() = runTest {
        val numberList = listOf(1, 2, 3, 4)
        val wordList = mutableListOf<String>()

        withContext(Dispatchers.IO) {
        numberList.forEach {

                wordList.add(fetchData(it).)
            }
        }

        wordList.forEach {
            println(it)
        }
    }


    suspend fun fetchData(i: Int): String {
        val rnds = (0..10).random()
        delay(TimeUnit.SECONDS.toMillis(rnds.toLong()))
        println("Tiempo que va a tardar la posicion $i: $rnds segundos")
        return "LLego el $i"
    }
}
