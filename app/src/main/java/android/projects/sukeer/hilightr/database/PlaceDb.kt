package android.projects.sukeer.hilightr.database

import android.content.Context
import android.projects.sukeer.hilightr.utility.App
import org.jetbrains.anko.db.rowParser

/**
 *
 * Author: Sukeerthi Khadri
 * Created: 10/10/16
 */
class PlaceDb(override val context: Context = App.instance) : DbDao<PlaceModel>() {

    // parse row of Cursor into model object
    override val parser = rowParser { _id: String, name: String, address: String, phone: String,
                                      website: String, latitude: Double, longitude: Double, type: Int, price: Int, rating: Int ->
        PlaceModel(_id, name, address, phone, website, latitude, longitude, type, price, rating)
    }

    override val constants = PlaceModel.constants
}