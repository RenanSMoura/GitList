package moura.silva.com.domain.executor

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler : Scheduler
}