# odoo-xml-external-api
Odoo external API in kotlin.<br/>
Read API Docs https://www.odoo.com/documentation/11.0/webservices/odoo.html

## Public APIs
`val client = OdooPublicApi.newInstance(url)`

#### Version
```
 val version = client.version()
 println(version)
```

#### Authenticate
```
  val username = "admin"
  val password = "admin"
  val userId = client.authenticate(username = username, password = password)
  println(userId)
```

<br/>
<br/>

## Authenticated APIs
`val odooApi = OdooApi.newInstance(url, database, userId, password)`

#### Create
```
val data = mapOf("name" to "Tean", "device_id" to 200192)
val result = odooApi.create("custom.preferences", data)
println(result)
```

####  Update
```
val modelName = "custom.patient"
val data = mapOf("surname" to "Tanya")
val result = odooApi.updateBatch(modelName, listOf(1, 12), data)
println(result)
```

#### Delete
```
val modelName = "custom.patient"
val result = odooApi.delete(modelName, 3)
println(result)
```


#### Check Access Rights
```
val isAllowed = odooApi.checkAccessRights("res.partner")
println(isAllowed)
```

#### Search IDs
```
val listOfIds: Array<Int> = odooApi.search("res.partner", arrayOf(arrayOf("id", "=", 1)))
println(listOfIds.joinToString())
```

#### Count
```
val count: Int = odooApi.count("res.partner", domainList = arrayOf(arrayOf("id", "=", 1)))
println(count)
```

#### Read
```
val result = odooApi.read("res.partner", arrayOf(1, 6))
println(result)
```

#### Search Read
```
val result = odooApi.searchRead("res.partner", filter = mapOf("offset" to 0, "limit" to 2))
println(result.joinToString())
```

#### Fields Get
```
val result = odooApi.fieldsGet("res.partner")
println(result)
```
