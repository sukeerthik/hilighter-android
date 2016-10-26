package android.projects.sukeer.hilightr.database

import android.content.Context
import android.projects.sukeer.hilightr.utility.App
import org.jetbrains.anko.db.rowParser

/**
 *
 * Author: Sukeerthi Khadri
 * Created: 10/16/16
 */
class HighlightDb(override val context: Context = App.instance) : DbDao<HighlightModel>() {

    // parse row of Cursor into model object
    override val parser = rowParser { _id: Long?, message: String?, person: String?, place: String?, date: Long? ->
        val params = mutableMapOf("_id" to _id, "message" to message, "person" to person, "place" to place, "date" to date)
        HighlightModel(params)
    }

    override val constants = HighlightModel.constants
}