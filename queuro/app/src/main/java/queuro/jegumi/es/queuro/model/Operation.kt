package queuro.jegumi.es.queuro.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@JsonIgnoreProperties(ignoreUnknown = true)
@RealmClass
open class Operation : RealmModel {

    @PrimaryKey
    open var id: Long = 0
    open var name: String? = ""
    open var description: String? = ""
    open var value: String? = ""
    open var date: String? = ""
}
