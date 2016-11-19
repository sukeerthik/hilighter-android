package android.projects.sukeer.hilightr.test

import android.projects.sukeer.hilightr.database.sqlitedb.*
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 *
 * Author: Sukeerthi Khadri
 * Created: 10/16/16
 */
@RunWith(AndroidJUnit4::class)
class HighlightDatabaseTest {

    companion object {
        lateinit private var highlightDb: HighlightDb
        lateinit private var placeDb: PlaceDb
        lateinit private var personDb: PersonDb

        private val validPlace = PlaceModel("1", "Willis Tower", "77 West Wacker", "18000000000", "www.google.com", 45.3909, 23.83291, 1, 1, 3)
        private val validPerson = PersonModel("1", "John Doe", "jdoe@gmail.com", "none")
        private val validHighlight = HighlightModel("my highlight", "Hello world", "1", "1", 1000)

        @BeforeClass @JvmStatic
        fun initialize() {
            val appContext = InstrumentationRegistry.getTargetContext()
            appContext.deleteDatabase("hilightr.db")
            highlightDb = HighlightDb(appContext)
            placeDb = PlaceDb(appContext)
            personDb = PersonDb(appContext)
        }
    }

    @Before
    fun prepareDatabase() {
        // clear table before every test
        highlightDb.clearTable()
        personDb.clearTable()
        placeDb.clearTable()
    }

    @Test
    fun testValidInsertion() {
        placeDb.addItem(validPlace)
        personDb.addItem(validPerson)
        val insertionId = highlightDb.addItem(validHighlight)

        val retrievedHighlight = highlightDb.getItem("_id", insertionId)

        Assert.assertNotNull("Retrieval failed", retrievedHighlight)
        Assert.assertEquals("_id invalid", insertionId, retrievedHighlight!!._id)
        Assert.assertEquals("Title invalid", validHighlight.title, retrievedHighlight.title)
        Assert.assertEquals("Message invalid", validHighlight.message, retrievedHighlight.message)
        Assert.assertEquals("Person _id invalid", validHighlight.person, retrievedHighlight.person)
        Assert.assertEquals("Place _id invalid", validHighlight.place, retrievedHighlight.place)
        Assert.assertEquals("Date invalid", validHighlight.date, retrievedHighlight.date)
    }

    @Test
    fun testInvalidInsertion() {
        // insertion should fail due to foreign key constraints
        val insertionId = highlightDb.addItem(validHighlight)
        Assert.assertEquals("Foreign key constraint not met", -1, insertionId)

        val retrievedHighlight = highlightDb.getItem("_id", insertionId)
        Assert.assertNull("Retrieval success, foreign key constraint not met", retrievedHighlight)
    }

    @Test
    fun testUpdate() {
        placeDb.addItem(validPlace)
        personDb.addItem(validPerson)
        val insertionId = highlightDb.addItem(validHighlight)

        // copy modified highlight and update in database
        val updatedHighlightMap = HashMap(validHighlight.map)
        updatedHighlightMap["_id"] = insertionId
        updatedHighlightMap["message"] = "Bye world!"
        val updatedValidHighlight = validHighlight.copy(updatedHighlightMap)
        highlightDb.updateItem(updatedValidHighlight)

        // assertions
        val retrievedHighlight = highlightDb.getItem("_id", insertionId)
        Assert.assertNotNull("Retrieval failed", retrievedHighlight)
        Assert.assertEquals("Message not updated", updatedValidHighlight.message, retrievedHighlight!!.message)
        Assert.assertEquals("Integrity invalid", validHighlight.place, retrievedHighlight.place)
    }

    @Test
    fun testRemove() {
        placeDb.addItem(validPlace)
        personDb.addItem(validPerson)
        highlightDb.addItem(validHighlight)

        // copy modified highlight and add to database for future deletion
        val highlightToDeleteMap = HashMap(validHighlight.map)
        highlightToDeleteMap["message"] = "Good morning world"

        // add highlight and check validity
        val insertionIdForDeletion = highlightDb.addItem(validHighlight.copy(highlightToDeleteMap))
        Assert.assertNotNull("Insertion failed", highlightDb.getItem("_id", insertionIdForDeletion))

        // remove highlight and check validity
        highlightDb.removeItem("_id", insertionIdForDeletion)
        Assert.assertNull("Deletion invalid", highlightDb.getItem("_id", insertionIdForDeletion))
        Assert.assertEquals("Size after deletion incorrect", 1, highlightDb.getAllItems().size)
    }

    @Test
    fun testRetrieveAllHighlights() {
        placeDb.addItem(validPlace)
        personDb.addItem(validPerson)
        highlightDb.addItem(validHighlight)

        // add additional highlight
        val newHighlight = HashMap(validHighlight.map)
        newHighlight["message"] = "What's up world"
        highlightDb.addItem(validHighlight.copy(newHighlight))

        val highlights = highlightDb.getAllItems()
        Assert.assertNotNull("Retrieval failed", highlights)
        Assert.assertEquals("Size incorrect", 2, highlights.size)
    }
}