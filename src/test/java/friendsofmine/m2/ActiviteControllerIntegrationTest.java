package friendsofmine.m2;

import friendsofmine.m2.domain.Activite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ActiviteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataLoader dataLoader;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private String jsonResult;


    @Test
    public void testFindAllActivitesWithResponsable() throws Exception {

        // ATTENTION : ICI C'EST LA SEULE FOIS Où VOUS AVEZ LE DROIT DE MODIFIER UN TEST !!!
        // changez l'activité récupérée dans le dataLoader
        // en fonction des activités que vous y avez créées
        Activite testedActivite = dataLoader.getPingpong();

        // when: l'utilisateur émet une requête pour obtenir la liste des activités
        mockMvc.perform(get("/activitesWithResponsable"))
                // then: la réponse a le status 200(OK)
                .andExpect(status().isOk())
                // then: la réponse est au format JSON et utf8
                .andExpect(content().contentType(contentType))
                .andDo(mvcResult -> {
                    jsonResult = mvcResult.getResponse().getContentAsString();
                });

        // then: le résultat obtenu contient le titre d'une activité persistée
        assertThat(jsonResult, containsString(testedActivite.getTitre()));
        // then: le résultat obtenu contient le descriptif d'une activité persistée
        assertThat(jsonResult, containsString(testedActivite.getDescriptif()));

    }

}
