package simple

import scalatags.JsDom.all._
import scalajs.concurrent.JSExecutionContext.Implicits.runNow
import org.scalajs.dom
import dom.html
import scalajs.js.annotation.JSExport
import autowire._

@JSExport
object Client extends{
  @JSExport
  def main(container: html.Div) = {
    val inputBox = input.render
    val outputBox = ul.render

    def update() = Ajaxer[Api].list(inputBox.value).call().foreach{ data =>
      outputBox.innerHTML = ""
      for(FileData(name, size) <- data){
        outputBox.appendChild(
          li(
            b(name), " - ", size, " bytes"
          ).render
        )
      }
    }

    inputBox.onkeyup = (e: dom.Event) => update()

    update()

    container.appendChild(
      div(
        h1("File Search"),
        inputBox,
        outputBox
      ).render
    )
  }
}