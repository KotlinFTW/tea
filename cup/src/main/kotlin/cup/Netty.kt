package cup

import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer
import org.jboss.resteasy.spi.ResteasyDeployment

fun attachNetty(port: Int, services: List<Any>): NettyJaxrsServer {

    val deployment = ResteasyDeployment()
    deployment.resources = services
    deployment.providers.add(RestJacksonProvider())

    val netty = NettyJaxrsServer()
    netty.deployment = deployment
    netty.port = port
    netty.setRootResourcePath("")
    return netty
}
