import processing.core.PApplet
import processing.core.PConstants
import processing.event.MouseEvent

class Main : PApplet() {
    private lateinit var centCircle: CentralCircle

    init {
       this.runSketch()
    }

    override fun settings() {
//        size(400, 400)
        fullScreen()
        pixelDensity(2)

        centCircle = CentralCircle(this)
    }

    override fun setup() {
    }

    fun drawSettings(){
        background(255)
        translate(width/2f, height/2f)
    }

    override fun draw() {
        drawSettings()
        centCircle.display()
    }

    override fun mouseWheel(event: MouseEvent?) {
        if (event!!.count > 0 && centCircle.sizeOfItem < 100f){
            centCircle.sizeOfItem += 20
            centCircle.distanceFromItem += centCircle.sizeOfItem/100
        } else if (event.count < 0 && centCircle.sizeOfItem > 0f){
            centCircle.sizeOfItem -= 20
            centCircle.distanceFromItem -= centCircle.sizeOfItem/100
        }
    }

}

fun main(args: Array<String>) {
    Main()
}