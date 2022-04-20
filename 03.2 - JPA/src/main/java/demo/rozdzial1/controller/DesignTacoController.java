package demo.rozdzial1.controller;

import demo.rozdzial1.data.IngredientRepository;
import demo.rozdzial1.data.TacoRepository;
import demo.rozdzial1.objects.Ingredient;
import demo.rozdzial1.objects.Order;
import demo.rozdzial1.objects.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import demo.rozdzial1.objects.Ingredient.Type;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private List<Ingredient> ingredients;
    private Type[] types;
    private final IngredientRepository ingredientRepo;
    private TacoRepository tacoRepository;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }
    @GetMapping
    public String showDesignForm(Model model) {
        ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        types = Ingredient.Type.values();

        for(Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(@ModelAttribute("design") @Valid Taco design,
                                Errors errors, Model model,
                                @ModelAttribute("order") Order order) {

        if(errors.hasErrors()) {
            for(Type type : types) {
                model.addAttribute(type.toString().toLowerCase(),
                        filterByType(ingredients, type));
            }
            return "design";
        }

        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);

        log.info("Przetwarzanie projektu taco: " + design);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
