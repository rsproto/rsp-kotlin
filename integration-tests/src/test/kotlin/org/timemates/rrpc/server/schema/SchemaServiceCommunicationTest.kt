package org.timemates.rrpc.server.schema

//object SchemaServiceCommunicationTest {
//    private val testScope = TestScope()
//
//    private val port = Random.nextInt(1000, 9999)
//
//    private val module = RRpcModule {
//        services {
//            schemaService()
//        }
//    }
//
//    @JvmStatic
//    private val server = embeddedServer(Netty, port = port) {
//            routing {
//                rrpcEndpoint(module = module)
//            }
//        }.start(false)
//
//    @JvmStatic
//    private var schemaClient: SchemaClient by Delegates.notNull()
//
//    @JvmStatic
//    @BeforeAll
//    fun setup(): Unit = runTest {
//        val client = HttpClient {
//            install(WebSockets)
//            install(RSocketSupport)
//        }
//        val rsocket = client.rSocket(urlString = "localhost:${port}/rrpc") {
//
//        }
//        schemaClient = SchemaService {
//            rsocket(rsocket)
//        }
//    }
//}