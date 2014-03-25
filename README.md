# Dyn SDK for Java - Developer Preview

NOTE: This is a developer preview - we welcome your feedback!
Please reach out via pull request or GitHub issue.

Making DNS Updates as easy as:

    // Configure/Authenticate the Dyn Java client instance
    ProviderMetadata meta = Providers.withId("dyn");
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


# API Endpoints Supported

* Session API: create/destroy
* Record API: AAAA A CNAME DNSKEY DS KEY LOC MX NS PTR RP SOA SRV TXT
* Zone API: list/create/get/update/delete/publish/freeze/thaw
* Geo/GeoRegion API: get/list
* GSLB/GSLB: Coming Soon

# Known Issues

* None yet
