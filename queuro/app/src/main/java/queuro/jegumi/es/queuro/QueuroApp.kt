package queuro.jegumi.es.queuro

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class QueuroApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: QueuroApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .name("queuro.realm")
                .schemaVersion(0)
                .build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}