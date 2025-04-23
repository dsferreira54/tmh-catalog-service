package fun.toomanyhats.resource;

import java.util.List;
import java.util.Optional;

import fun.toomanyhats.dto.HatDTO;
import fun.toomanyhats.model.Artisan;
import fun.toomanyhats.model.Hat;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;

@Path("/hats")
public class HatResource {
    @GET
    @Produces("application/json")
    public List<Hat> listAll() {
        return Hat.listAll();
    }

    @GET
    @Path("/find")
    @Produces("application/json")
    public Optional<Hat> findByNameAndColor(@QueryParam("name") String name, @QueryParam("color") String color) {
        return Hat.find("name = ?1 and color = ?2", name, color).firstResultOptional();
    }

    @GET
    @Path("/filter")
    @Produces("application/json")
    public List<Hat> filterByColor(@QueryParam("color") String color) {
        return Hat.find("color", color).list();
    }

    @POST
    @Path("/count")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Long countByArtisanId(@FormParam("artisanId") Long artisanId) {
        return Hat.find("artisan.id", artisanId).count();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Response create(HatDTO hatDTO) {
        Artisan artisan = Artisan.findById(hatDTO.artisanId);
        if (artisan == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Artisan with id " + hatDTO.artisanId + " not found.")
                    .build();
        }

        Hat hat = new Hat();

        hat.name = hatDTO.name;
        hat.color = hatDTO.color;
        hat.artisan = artisan;

        hat.persist();
        
        return Response.status(Response.Status.CREATED).entity(hat).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(Long id) {
        Hat hat = Hat.findById(id);
        if (hat == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Hat with id " + id + " not found.")
                    .build();
        }
        
        hat.delete();

        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Response update(@PathParam("id") Long id, HatDTO hatDTO) {
        Hat hat = Hat.findById(id);
        if (hat == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Hat with id " + id + " not found.")
                    .build();
        }

        Artisan artisan = Artisan.findById(hatDTO.artisanId);
        if (artisan == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Artisan with id " + hatDTO.artisanId + " not found.")
                    .build();
        }

        hat.name = hatDTO.name;
        hat.color = hatDTO.color;
        hat.artisan = artisan;

        return Response.status(Response.Status.CREATED).entity(hat).build();
    }
}
