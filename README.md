# Dyn SDK for Java

NOTE: This SDK is brand new - we welcome your feedback!
Please reach out via pull request or GitHub issue.

Using the library is super-easy with Maven:

    <dependency>
        <groupId>com.dyn</groupId>
        <artifactId>dyn-client</artifactId>
        <version>1.0.0</version>
    </dependency>

Making DNS Updates as easy as:

    // Configure/Authenticate the Dyn Java client instance
    ProviderMetadata meta = Providers.withId("dyn-traffic");
    ContextBuilder ctx = ContextBuilder.newBuilder(meta);
    ctx.credentials("yourcustomer:youruser", "yourpass");
    DynTrafficApi dyn = ctx.buildApi(DynTrafficApi.class);

    // obtain Record and Zone API instances to make changes
    ZoneApi zone = dyn.getZoneApi();
    RecordApi records = dyn.getRecordApiForZone("example.com");

    // submit the changes
    records.scheduleCreate(CreateRecord.<CNAMEData> builder().type("CNAME")
    .fqdn("myapp.example.com.")
    .rdata(CNAMEData.cname("example.herokuapp.com.")).build());

    // publish the changes
    zone.publish("example.com");

Working with the Message API is as easy as:

    // Configure/Authenticate the Dyn Java Messaging client instance
    ProviderMetadata meta = Providers.withId("dyn-messaging");
    ContextBuilder ctx = ContextBuilder.newBuilder(meta);
    
    ctx.credentials(/* intentionally blank -> */"", "yourapikey");
    DynMessagingApi dyn = ctx.buildApi(DynMessagingApi.class);

    SendMailApi send = dyn.getSendMailApi();
    System.out.println("send : "
    		+ send.sendMessage("fromperson@example.org",
    				"recipient@example.org", "the subject", "hi text",
    				"<p>hi html</p>", "ccuser@example.org",
    				"reply-to@example.org", null));
    
    AccountsApi accounts = dyn.getAccountsApi();
    
    System.out.println("accounts list : " + accounts.list(0));
    System.out.println("accounts xheaders : " + accounts.getXHeaders());
    
    RecipientsApi recipients = dyn.getRecipientsApi();
    
    System.out.println("recipient status : "
    		+ recipients.status("recip@example.com"));
    System.out.println("recipient activate : "
    		+ recipients.activate("recip@example.com"));
    
    Map<String, ReportApi> apis = ImmutableMap
    		.<String, ReportApi> builder()
    		.put("bounces", dyn.getBounceReportApi())
    		.put("clicks", dyn.getClicksReportApi())
    		.put("complaints", dyn.getComplaintsReportApi())
    		.put("delivered", dyn.getDeliveredReportApi())
    		.put("issues", dyn.getIssuesReportApi())
    		.put("opens", dyn.getOpensReportApi())
    		.put("sent", dyn.getSentReportApi()).build();

    // test the regular report functionality
    for (Map.Entry<String, ReportApi> entry : apis.entrySet()) {
    	String name = entry.getKey();
    	ReportApi api = entry.getValue();
    
    	System.out.println(name + " list : "
    			+ api.list("2014-01-01", "2014-11-01", 0));
    	System.out.println(name + " count : "
    			+ api.count("2014-01-01", "2014-11-01"));
    }

    Map<String, UniqueReportApi> unique = ImmutableMap
    		.<String, UniqueReportApi> builder()
    		.put("clicks", dyn.getClicksReportApi())
    		.put("opens", dyn.getOpensReportApi()).build();

    // unique report functionality
    for (Map.Entry<String, UniqueReportApi> entry : unique.entrySet()) {
    	String name = entry.getKey();
    	UniqueReportApi api = entry.getValue();
    
    	System.out.println(name + " list unique : "
    			+ api.listUnique("2014-01-01", "2014-11-01", 0));
    	System.out.println(name + " count unique : "
    			+ api.countUnique("2014-01-01", "2014-11-01"));
    }

# API Endpoints Supported

* Traffic - Session API: create/destroy
* Traffic - Record API: AAAA A CNAME DNSKEY DS KEY LOC MX NS PTR RP SOA SRV TXT
* Traffic - Zone API: list/create/get/update/delete/publish/freeze/thaw
* Traffic - Geo/GeoRegion API: get/list
* Messaging - All Endpoints Supported

# Known Issues

* None yet

# License

* Apache 2.0

