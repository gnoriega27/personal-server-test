package com.gabrielnoriega

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import io.ktor.auth.jwt.jwt
import io.ktor.jackson.*
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}


//fun Application.module() {
//    val simpleJwt = SimpleJWT("my-super-secret-for-jwt")
//
//    install(Authentication) {
//        jwt {
//            verifier(simpleJwt.verifier)
//            validate {
//                UserIdPrincipal(it.payload.getClaim("name").asString())
//            }
//        }
//    }
//    install(ContentNegotiation) {
//        jackson { enable(SerializationFeature.INDENT_OUTPUT) }
//    }
//    routing {
//        route("/snippets") {
//            get {
//                call.respond(mapOf("snippets" to synchronized(snippets) { snippets.toList() }))
//            }
//            authenticate {
//                post {
//                    val post = call.receive<PostSnippet>()
//                    snippets += Snippet(post.snippet.text)
//                    call.respond(mapOf("OK" to true))
//                }
//            }
//        }
//
//        post("/login-register") {
//            val post = call.receive<LoginRegister>()
//            val user = users.getOrPut(post.user) { User(post.user, post.password) }
//            if (user.password != post.password) error("Invalid credentials")
//            call.respond(mapOf("token" to simpleJwt.sign(user.name)))
//        }
//    }
//}
//
//class User(val name: String, val password: String)
//
//val users = Collections.synchronizedMap(
//    listOf(User("test", "test"))
//        .associateBy { it.name }
//        .toMutableMap()
//)
//
//class LoginRegister(val user: String, val password: String)
//
//
//open class SimpleJWT(val secret: String) {
//    private val algorithm = Algorithm.HMAC256(secret)
//    val verifier = JWT.require(algorithm).build()
//    fun sign(name: String): String = JWT.create().withClaim("name", name).sign(algorithm)
//}
//
//data class Snippet(val text: String)
//data class PostSnippet(val snippet: PostSnippet.Text) {
//    data class Text(val text: String)
//}
//
//val snippets = Collections.synchronizedList(
//    mutableListOf(
//        Snippet("hello"),
//        Snippet("world")
//    )
//)


