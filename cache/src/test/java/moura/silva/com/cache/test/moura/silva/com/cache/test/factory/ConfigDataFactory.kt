package moura.silva.com.cache.test.moura.silva.com.cache.test.factory

import moura.silva.com.cache.model.Config


object ConfigDataFactory {

    fun makeCachedConfig(): Config {
        return Config(DataFactory.randomInt(), DataFactory.randomLong())
    }

}