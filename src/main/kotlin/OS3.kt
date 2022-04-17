import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

@ObsoleteCoroutinesApi
fun main() = runBlocking {


        repeat(countThreads) {
                if (it <= producers)
                    ProducerThread().start()
                else
                    ConsumerThread().start()
        }



    val input = Scanner(System.`in`)

    println("Запуск конвейера")
    println("Для остановки нажмите 'q'")


    do {
        val stop = input.next()
    } while (stop != "q")

    finish = false

    println("Кол-во элементов в очереди: ${queue.itemCount()}")
}


private class ProducerThread: Thread() {
    private val random = Random()

    override fun run() {
        super.run()
        while (finish) {
            if (queue.itemCount() >= 100) {
                hundr=true
                sleep(timeSleep)
                continue
            }
            if (!hundr){
            val value = random.nextInt(100) + 1
            queue.add(value)
            println("Количество элементов в очереди: ${queue.itemCount()}")}
            sleep(600)
        }
    }
}

private class ConsumerThread: Thread() {
    override fun run() {
        super.run()

        while (finish) {
            if (queue.isEmpty()) {
                sleep(timeSleep)
                continue
            }
            if (!(hundr&&queue.itemCount()<=80)){
            queue.remove()
            println("Количество элементов в очереди: ${queue.itemCount()}")} else hundr=false
            sleep(300)
        }
        while(!queue.isEmpty()){
            queue.remove()
            println("Количество элементов в очереди: ${queue.itemCount()}")
        }
    }
}

private val queue = Queue<Int>(200)
private const val consumers = 2
private const val producers = 3
private const val countThreads = consumers + producers

private const val timeSleep = 600L
private var hundr=false
private var finish = true


class Queue<E>(private val size: Int) {
    private val data = ArrayList<E>()

    @Synchronized
    fun itemCount() = data.size

    @Synchronized
    fun add(element: E) {
        if (data.size < size) data += element
    }

    @Synchronized
    fun remove() = if (itemCount() > 0)
        data.removeAt(0)
    else null

    @Synchronized
    fun isEmpty() =
        itemCount() == 0
}
