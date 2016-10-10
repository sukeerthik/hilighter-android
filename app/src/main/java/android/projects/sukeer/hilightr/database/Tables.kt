package android.projects.sukeer.hilightr.database

/**
 *
 * Author: Sukeerthi Khadri
 * Created: 10/9/16
 */

object Place {
    val TABLE_NAME = "place"
    val COL_ID = "_id"
    val COL_NAME = "name"
    val COL_ADDR = "address"
    val COL_PHONE = "phone"
    val COL_WEB = "website"
    val COL_LAT = "latitude"
    val COL_LON = "longitude"
    val COL_TYPE = "type"
    val COL_PRICE = "price"
    val COL_RATING = "rating"
}

object Person {
    val TABLE_NAME = "person"
    val COL_ID = "_id"
    val COL_NAME = "name"
    val COL_EMAIL = "email"
    val COL_PHOTO = "photo"
    val COL_TOKEN = "token"
}

object Highlight {
    val TABLE_NAME = "highlight"
    val COL_ID = "_id"
    val COL_MESSAGE = "message"
    val COL_PERSON = "person"
    val COL_PLACE = "place"
    val COL_DATE = "date"
}

object Record {
    val TABLE_NAME = "record"
    val COL_ID = "_id"
    val COL_PER_ID = "person"
    val COL_HIGHLIGHT_ID = "highlight"
    val COL_PLACE_ID = "place_id"
}

