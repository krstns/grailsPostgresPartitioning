import java.text.SimpleDateFormat
import pl.cluain.test.grailsPartition.TestDomain

class BootStrap {

    def init = { servletContext ->
        def sdf = new SimpleDateFormat("yyyy.MM.dd")



        TestDomain blog = new TestDomain(someDate: sdf.parse("2012.03.01"))
        blog.save()

        TestDomain blog1 = new TestDomain(someDate: sdf.parse("2012.02.15"))
        blog1.save()

        TestDomain blog2 = new TestDomain(someDate: sdf.parse("2012.03.05"))
        blog2.save()

        println("should be saved now")

        println(TestDomain.count())

        TestDomain.list().each {
            println(it.someDate)
        }
    }
    def destroy = {
    }
}
