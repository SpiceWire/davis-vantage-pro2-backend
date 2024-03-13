package spicewire.davisinterface.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.util.List;

public class CensusAddress {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "zip",
            "city",
            "street",
            "state"
    })
    @Generated("jsonschema2pojo")
    public class Address {

        @JsonProperty("zip")
        public String zip;
        @JsonProperty("city")
        public String city;
        @JsonProperty("street")
        public String street;
        @JsonProperty("state")
        public String state;

        /**
         * No args constructor for use in serialization
         *
         */
        public Address() {
        }

        /**
         *
         * @param zip
         * @param city
         * @param street
         * @param state
         */
        public Address(String zip, String city, String street, String state) {
            super();
            this.zip = zip;
            this.city = city;
            this.street = street;
            this.state = state;
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
    @Generated("jsonschema2pojo")
    public class AddressComponents {

        @JsonProperty("zip")
        public String zip;
        @JsonProperty("streetName")
        public String streetName;
        @JsonProperty("preType")
        public String preType;
        @JsonProperty("city")
        public String city;
        @JsonProperty("preDirection")
        public String preDirection;
        @JsonProperty("suffixDirection")
        public String suffixDirection;
        @JsonProperty("fromAddress")
        public String fromAddress;
        @JsonProperty("state")
        public String state;
        @JsonProperty("suffixType")
        public String suffixType;
        @JsonProperty("toAddress")
        public String toAddress;
        @JsonProperty("suffixQualifier")
        public String suffixQualifier;
        @JsonProperty("preQualifier")
        public String preQualifier;

        /**
         * No args constructor for use in serialization
         *
         */
        public AddressComponents() {
        }

        /**
         *
         * @param zip
         * @param streetName
         * @param preType
         * @param city
         * @param preDirection
         * @param suffixDirection
         * @param fromAddress
         * @param state
         * @param suffixType
         * @param toAddress
         * @param suffixQualifier
         * @param preQualifier
         */
        public AddressComponents(String zip, String streetName, String preType, String city, String preDirection, String suffixDirection, String fromAddress, String state, String suffixType, String toAddress, String suffixQualifier, String preQualifier) {
            super();
            this.zip = zip;
            this.streetName = streetName;
            this.preType = preType;
            this.city = city;
            this.preDirection = preDirection;
            this.suffixDirection = suffixDirection;
            this.fromAddress = fromAddress;
            this.state = state;
            this.suffixType = suffixType;
            this.toAddress = toAddress;
            this.suffixQualifier = suffixQualifier;
            this.preQualifier = preQualifier;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "tigerLine",
            "coordinates",
            "addressComponents",
            "matchedAddress"
    })
    @Generated("jsonschema2pojo")
    public class AddressMatch {

        @JsonProperty("tigerLine")
        public TigerLine tigerLine;
        @JsonProperty("coordinates")
        public Coordinates coordinates;
        @JsonProperty("addressComponents")
        public AddressComponents addressComponents;
        @JsonProperty("matchedAddress")
        public String matchedAddress;

        /**
         * No args constructor for use in serialization
         *
         */
        public AddressMatch() {
        }

        /**
         *
         * @param tigerLine
         * @param coordinates
         * @param addressComponents
         * @param matchedAddress
         */
        public AddressMatch(TigerLine tigerLine, Coordinates coordinates, AddressComponents addressComponents, String matchedAddress) {
            super();
            this.tigerLine = tigerLine;
            this.coordinates = coordinates;
            this.addressComponents = addressComponents;
            this.matchedAddress = matchedAddress;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "isDefault",
            "benchmarkDescription",
            "id",
            "benchmarkName"
    })
    @Generated("jsonschema2pojo")
    public class Benchmark {

        @JsonProperty("isDefault")
        public Boolean isDefault;
        @JsonProperty("benchmarkDescription")
        public String benchmarkDescription;
        @JsonProperty("id")
        public String id;
        @JsonProperty("benchmarkName")
        public String benchmarkName;

        /**
         * No args constructor for use in serialization
         *
         */
        public Benchmark() {
        }

        /**
         *
         * @param isDefault
         * @param benchmarkDescription
         * @param id
         * @param benchmarkName
         */
        public Benchmark(Boolean isDefault, String benchmarkDescription, String id, String benchmarkName) {
            super();
            this.isDefault = isDefault;
            this.benchmarkDescription = benchmarkDescription;
            this.id = id;
            this.benchmarkName = benchmarkName;
        }

    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "x",
            "y"
    })
    @Generated("jsonschema2pojo")
    public class Coordinates {

        @JsonProperty("x")
        public Double x;
        @JsonProperty("y")
        public Double y;

        /**
         * No args constructor for use in serialization
         *
         */
        public Coordinates() {
        }

        /**
         *
         * @param x
         * @param y
         */
        public Coordinates(Double x, Double y) {
            super();
            this.x = x;
            this.y = y;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "result"
    })
    @Generated("jsonschema2pojo")
    public class Example {

        @JsonProperty("result")
        public Result result;

        /**
         * No args constructor for use in serialization
         *
         */
        public Example() {
        }

        /**
         *
         * @param result
         */
        public Example(Result result) {
            super();
            this.result = result;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "address",
            "benchmark"
    })
    @Generated("jsonschema2pojo")
    public class Input {

        @JsonProperty("address")
        public Address address;
        @JsonProperty("benchmark")
        public Benchmark benchmark;

        /**
         * No args constructor for use in serialization
         *
         */
        public Input() {
        }

        /**
         *
         * @param address
         * @param benchmark
         */
        public Input(Address address, Benchmark benchmark) {
            super();
            this.address = address;
            this.benchmark = benchmark;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "input",
            "addressMatches"
    })
    @Generated("jsonschema2pojo")
    public class Result {

        @JsonProperty("input")
        public Input input;
        @JsonProperty("addressMatches")
        public List<AddressMatch> addressMatches;

        /**
         * No args constructor for use in serialization
         *
         */
        public Result() {
        }

        /**
         *
         * @param input
         * @param addressMatches
         */
        public Result(Input input, List<AddressMatch> addressMatches) {
            super();
            this.input = input;
            this.addressMatches = addressMatches;
        }

        public String gimmieString() {
            return "Hey, it's a result" + input.address.toString();
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "side",
            "tigerLineId"
    })


    public class TigerLine {

        @JsonProperty("side")
        public String side;
        @JsonProperty("tigerLineId")
        public String tigerLineId;

        /**
         * No args constructor for use in serialization
         *
         */
        public TigerLine() {
        }

        /**
         *
         * @param side
         * @param tigerLineId
         */
        public TigerLine(String side, String tigerLineId) {
            super();
            this.side = side;
            this.tigerLineId = tigerLineId;
        }

    }


}
