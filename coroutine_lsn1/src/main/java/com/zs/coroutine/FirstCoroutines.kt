package com.zs.coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @Author zhangshuai
 * @Date 2021/9/5
 * @Emial zhangshuai@dushu365.com
 * @Description 基础
 */
class FirstCoroutines {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            //runBlocking 阻塞当前线程来等待。是常规函数
            launch {
                showDialog("1....")
                showDialog("2....")
                showDialog("3....")
                showDialog("4....")

                show()

                println("Task from runBlocking launch -> before")
                delay(1000L)
                println("2-Task from runBlocking launch")
            }
            //coroutineScope只是挂起，会释放底层线程用于其他用途。是挂起函数
            coroutineScope {
                launch {
                    println("Task from nested launch ->before")
                    doWork()
                }
                delay(100L)
                println("1-Task from coroutineScope")
            }
            println("4-Coroutine Scope is over")
        }

        private suspend fun doWork() {
            delay(2000L)
            println("3-Task from nested launch")
        }

        private suspend fun showDialog(str: String) = suspendCancellableCoroutine<Unit> {
            println("showDialog：$str")
            it.resume(Unit)
        }

        private suspend fun show()= suspendCoroutine<Unit> {
            println("show()")
            it.resume(Unit)
        }
    }
}