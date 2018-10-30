import processing.core.PApplet
import processing.core.PApplet.*
import processing.core.PConstants
import processing.core.PVector
import java.beans.PropertyVetoException

class CentralCircle(val sketch: PApplet) {
    var vertices = mutableListOf<Vertex>()
    private var circleNotSetup = true
    val r: Float by lazy {
        sketch.width/4f
    }

    var distanceFromItem = 0f
    var sizeOfItem = 10f

    fun setupCircleParameters() {
        if (circleNotSetup) {
            distanceFromItem = sqrt(sketch.width.toFloat()*sketch.height.toFloat()) * 0.1f
            val sides = 360
            val angle = 360 / sides
            for (i in 0 until sides) {
                val theta = radians(i * angle.toFloat())
                val x = r * cos(theta)
                val y = r * sin(theta)
                vertices.add(Vertex(PVector(x, y)))
            }
            circleNotSetup = false
        }
    }

    fun display() {
        setupCircleParameters()

        sketch.beginShape()
        for (vertex in vertices) {
            val currentMousePosition = PVector(sketch.mouseX.toFloat() - sketch.width / 2, sketch.mouseY.toFloat() - sketch.height / 2)
            val centre = PVector(0f, 0f)
            var force = r
            val distanceBetweenObjectAndVertexOrigin = PVector.dist(currentMousePosition, vertex.originLocation)
            val distanceBetweenObjectAndVertexCurrent = PVector.dist(currentMousePosition, vertex.currentLocation)

            vertex.currentLocation.normalize()
            if (distanceBetweenObjectAndVertexOrigin < distanceFromItem){
                //Changing the below map value from the 'origin' to the 'current' vertex presents a cool flickering waveform effect
                val vertexLimiter = map(distanceBetweenObjectAndVertexOrigin, 0f, distanceFromItem, sizeOfItem, 0f)
                force -= vertexLimiter*0.5f
                vertex.currentLocation.mult(force)
            } else {
                vertex.currentLocation.mult(r)
            }

            sketch.vertex(vertex.currentLocation.x, vertex.currentLocation.y)
        }
        sketch.endShape(PConstants.CLOSE)

    }
}