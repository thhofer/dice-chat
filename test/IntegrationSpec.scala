import com.gargoylesoftware.htmlunit.BrowserVersion
import org.junit.runner._
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  "Application" should {

    "work from within a browser" in new WithBrowser(webDriver = new HtmlUnitDriver(BrowserVersion.CHROME)) {

      browser.goTo("http://localhost:" + port)

      browser.pageSource must contain("Welcome to DiceChat!")
    }
  }
}
