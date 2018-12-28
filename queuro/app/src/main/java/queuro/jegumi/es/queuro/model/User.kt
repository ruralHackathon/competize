package queuro.jegumi.es.queuro.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@JsonIgnoreProperties(ignoreUnknown = true)
@RealmClass
open class User : RealmModel {

    @PrimaryKey
    open var id: Long = 0
    open var dni: String = ""
    open var name: String? = ""
    open var surname: String? = ""
    open var card_valid: String? = ""
    open var card_number: String? = ""

    fun getFullName(): String {
        return "$name $surname"
    }
}
