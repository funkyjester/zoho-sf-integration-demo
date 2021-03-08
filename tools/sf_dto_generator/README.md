# Generating SF DTO objects

You may need to re-run this on schema changes.

Edit the pom.xml and set the appropriate credentials in the properties:

```xml
	<properties>
		<!-- salesforce properties -->
		<camelSalesforce.clientId>[connected app oauth settings - consumer key]</camelSalesforce.clientId>
		<camelSalesforce.clientSecret>[connected app oauth settings - consumer secret]</camelSalesforce.clientSecret>
		<camelSalesforce.userName>[your SF account email]</camelSalesforce.userName>
		<camelSalesforce.password>[your SF account password]</camelSalesforce.password>
		<camelSalesforce.token>[generate via settings - Reset My Security Token]</camelSalesforce.token>
		<camelSalesforce.loginUrl>[your login url displayed in profile menu]</camelSalesforce.loginUrl>

	</properties>
```

Run this:

```shell script
mvn camel-salesforce:generate
```

This will generate the DTO objects in the target directory