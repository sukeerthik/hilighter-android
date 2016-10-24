package android.projects.sukeer.hilightr.database

import android.content.Context
import android.projects.sukeer.hilightr.utility.App
import android.projects.sukeer.hilightr.utility.clear
import android.projects.sukeer.hilightr.utility.toVarArgArray
import org.jetbrains.anko.db.*

/**
 *
 * Author: Sukeerthi Khadri
 * Created: 10/15/16
 */
class PersonDb(val context: Context = App.instance) {

    private val dbHelper: DbHelper
        get() =
        if (context == App.instance) {
            DbHelper.instance
        } else {
            DbHelper(context)
        }

    // parse row of Cursor into model object
    private val parser = rowParser { _id: String, name: String, email: String, photo: String, uid: String ->
        PersonModel(_id, name, email, photo, uid)
    }

    fun clearTable() = dbHelper.use {
        clear(PersonConstant.TABLE_NAME)
    }

    fun addPerson(person: PersonModel) = dbHelper.use {
        with(person) {
            insert(PersonConstant.TABLE_NAME, *map.toVarArgArray())
        }
    }

    fun updatePerson(person: PersonModel) = dbHelper.use {
        with(person) {
            update(PersonConstant.TABLE_NAME, *map.toVarArgArray())
                    .where("_id = {personId}", "personId" to _id)
                    .exec()
        }
    }

    fun removePerson(id: String) = dbHelper.use {
        delete(PersonConstant.TABLE_NAME, "_id = {personId}", "personId" to id)
    }

    fun getPerson(id: String) = dbHelper.use {
        select(PersonConstant.TABLE_NAME)
                .where("_id = {personId}", "personId" to id)
                .parseOpt(parser)
    }

    fun getAllPersons() = dbHelper.use {
        select(PersonConstant.TABLE_NAME)
                .exec { parseList(parser) }
    }

}