package spicewire.davisinterface.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import java.util.List;

//generated by "jsonschema2pojo" https://www.jsonschema2pojo.org/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "result"
})
public class CensusGeolookup {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "input",
            "addressMatches"
    })
    public  class Result {
        @JsonProperty("input") public   Input input;
        @JsonProperty("addressMatches") public List<AddressMatch> addressMatches;


        public  String gimmieCity() {
            return input.address.city;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "address",
            "benchmark"
    })
    public  class Input {
        @JsonProperty("address") public Address address;
        @JsonProperty("benchmark") public Benchmark benchmark;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "zip",
            "city",
            "street",
            "state"
    })
    public class Address {
        @JsonProperty("zip") public String zip;
        @JsonProperty("city") public String city;
        @JsonProperty("street") public String street;
        @JsonProperty("state") public String state;

        @Override
        public String toString(){
            return street + ", " + city + ", " + state + ", " + zip;
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "zip",
            "streetName",
            "preType",
            "city",
            "preDirection",
            "suffixDirection",
            "fromAddress",
            "state",
            "suffixType",
            "toAddress",
            "suffixQualifier",
            "preQualifier"
    })
    public  class AddressComponents {

        @JsonProperty("zip") public String zip;
        @JsonProperty("streetName") public String streetName;
        @JsonProperty("preType") public String preType;
        @JsonProperty("city") public String city;
        @JsonProperty("preDirection") public String preDirection;
        @JsonProperty("suffixDirection") public String suffixDirection;
        @JsonProperty("fromAddress") public String fromAddress;
        @JsonProperty("state") public String state;
        @JsonProperty("suffixType") public String suffixType;
        @JsonProperty("toAddress") public String toAddress;
        @JsonProperty("suffixQualifier") public String suffixQualifier;
        @JsonProperty("preQualifier") public String preQualifier;

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "tigerLine",
            "coordinates",
            "addressComponents",
            "matchedAddress"
    })
    public  class AddressMatch {
        @JsonProperty("tigerLine") public TigerLine tigerLine;
        @JsonProperty("coordinates") public Coordinates coordinates;
        @JsonProperty("addressComponents") public AddressComponents addressComponents;
        @JsonProperty("matchedAddress") public String matchedAddress;

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "isDefault",
            "benchmarkDescription",
            "id",
            "benchmarkName"
    })
    public  class Benchmark {

        @JsonProperty("isDefault") public Boolean isDefault;
        @JsonProperty("benchmarkDescription") public String benchmarkDescription;
        @JsonProperty("id") public String id;
        @JsonProperty("benchmarkName") public String benchmarkName;

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "x",
            "y"
    })
    public  class Coordinates {

        @JsonProperty("x") public Double x;
        @JsonProperty("y") public Double y;

    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "side",
            "tigerLineId"
    })
    public  class TigerLine {
        @JsonProperty("side") public String side;
        @JsonProperty("tigerLineId") public String tigerLineId;
    }
}