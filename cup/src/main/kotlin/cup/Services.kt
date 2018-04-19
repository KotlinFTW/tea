package cup

import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Files.readAllBytes
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ok


/**
 * Wraps a method call in something that returns a Response in the form that the REST service wants, decorated with the right headers and such.
 */
fun wrap(method: () -> Any): Response {
    try {
        val result = method()
        return (if (result is Unit) Response.ok() else Response.ok(result, MediaType.APPLICATION_JSON_TYPE)).header("Access-Control-Allow-Origin", "*").build()
    }
    catch (e : Exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").build()
    }
}


@Path("/cc")
@Produces(MediaType.APPLICATION_JSON)
class MarketDataService {

    val pricePoller = MarketData()

    @GET
    @Path("/")
    fun getPrices() = wrap({ pricePoller.getPrices() })

    @OPTIONS
    @Path("/{any:.*}")
    fun options() = Response.status(Response.Status.OK)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
            .header("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS")
            .build()
}


/**
 * This is the thing that serves up the static content
 * Probably should be Apache or something, but this is easy enough
 */
@Path("/")
class WebService {

    val webappPath = FileSystems.getDefault().getPath("../leaves/src/main/html")

    //todo: this path stuff is pretty contrived.  make it so the javascript is generated into the html folder or something more sensible
    val jsPath = FileSystems.getDefault().getPath("../leaves/build/classes/kotlin/main")
    val jsLibPath = FileSystems.getDefault().getPath("../leaves/lib")

    @GET
    @Path("/")
    @Produces("text/html")
    fun default() = html("index")

    @GET
    @Path("/{name}.html")
    @Produces("text/html")
    fun html(@PathParam("name") name: String) = get(webappPath, "$name.html")

    @GET
    @Path("/{name}.css")
    @Produces("text/css")
    fun css(@PathParam("name") name: String) = get(webappPath, "$name.css")

    @GET
    @Path("/js/{name}")
    @Produces("application/javascript")
    fun js(@PathParam("name") name: String) = get(jsPath, name)

    @GET
    @Path("/js/lib/{name}")
    @Produces("application/javascript")
    fun jsLib(@PathParam("name") name: String) = get(jsLibPath, name)

    private fun get(path: java.nio.file.Path, name: String): Response {
        println("Getting $name from " + path.toAbsolutePath())
        val poke = File(".");
        println(poke.absolutePath);
        val file = path.resolve(name)
        return (
            if (file.toFile().exists())
                ok(readAllBytes(file))
            else
                Response.status(Response.Status.NOT_FOUND))
        .build()
    }

}
