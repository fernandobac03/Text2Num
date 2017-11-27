package ec.edu.ucuenca.cepraXI.webservices;

import ec.edu.ucuenca.cepraXI.application.text2Numeric;
import ec.edu.ucuenca.cepraXI.application.tfidf;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/service")
public class RestServices {
    @GET
    @Path("/testing/{param}")
    public Response printMessage(@PathParam("param") String msg) {
        String result = "test";
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/tfidf")
    @Consumes("application/json")
    public Response createTFIDFMatrix(String jsondocuments) {
        text2Numeric newnumericmatrix = new text2Numeric();
        String result = newnumericmatrix.getNumericMatrix(jsondocuments, "tfidf");
        return Response.status(201).entity(result).build();

    }

    @POST
    @Path("/binary")
    @Consumes("application/json")
    public Response createBINARYMatrix(String jsondocuments) {
        text2Numeric newnumericmatrix = new text2Numeric();
        String result = newnumericmatrix.getNumericMatrix(jsondocuments, "binary");
        return Response.status(201).entity(result).build();

    }

    @POST
    @Path("/frequency")
    @Consumes("application/json")
    public Response createFREQUENCYMatrix(String jsondocuments) {
        text2Numeric newnumericmatrix = new text2Numeric();
        String result = newnumericmatrix.getNumericMatrix(jsondocuments, "frequency");
        return Response.status(201).entity(result).build();
    }
}
