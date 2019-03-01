package real_estate_agency_app.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import real_estate_agency_app.domain.models.service.OfferServiceModel;
import real_estate_agency_app.domain.models.view.OfferHomeViewModel;
import real_estate_agency_app.service.OfferService;
import real_estate_agency_app.utils.MyFileReader;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final String INDEX_FILE_RELATIVE_PATH = "static/index.html";

    private OfferService offerService;
    private ModelMapper modelMapper;
    private MyFileReader myFileReader;

    @Autowired
    public HomeController(OfferService offerService, ModelMapper modelMapper, MyFileReader myFileReader) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
        this.myFileReader = myFileReader;
    }

    @GetMapping
    @ResponseBody //for returning a string and not a page
    public String getHome() throws IOException {
        List<OfferServiceModel> allOffers = this.offerService.getAll();
        List<OfferHomeViewModel> viewModels = allOffers
                .stream()
                .map(offer -> this.modelMapper.map(offer, OfferHomeViewModel.class))
                .sorted(Comparator.comparing(OfferHomeViewModel::getApartmentType))
                .collect(Collectors.toList());

        String offersHtml = this.getModelsAsDivs(viewModels);
        String indexFileHtml = this.myFileReader.getContentFromRelativePath(INDEX_FILE_RELATIVE_PATH);

        return indexFileHtml.replace("{{allOffers}}", offersHtml);
    }

    private String getModelsAsDivs(List<OfferHomeViewModel> viewModels) {
        StringBuilder sb = new StringBuilder();
        for (OfferHomeViewModel viewModel : viewModels) {
            sb.append(this.convertViewModelToHtmlDiv(viewModel)).append(System.lineSeparator());
        }

        return sb.toString();
    }

    private String convertViewModelToHtmlDiv(OfferHomeViewModel offerHomeViewModel) {
        return String.format("" +
                "<div class=\"apartment\">\n" +
                "<p>Rent: %.2f</p>\n" +
                "<p>Type: %s</p>\n" +
                "<p>Commission: %.2f</p>\n" +
                "</div>"
        , offerHomeViewModel.getApartmentRent(),
                offerHomeViewModel.getApartmentType(),
                offerHomeViewModel.getAgencyCommission());
    }
}
