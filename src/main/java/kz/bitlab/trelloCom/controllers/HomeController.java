package kz.bitlab.trelloCom.controllers;

import java.util.ArrayList;
import java.util.List;
import kz.bitlab.trelloCom.entities.Categories;
import kz.bitlab.trelloCom.entities.Folders;
import kz.bitlab.trelloCom.entities.Tasks;
import kz.bitlab.trelloCom.entities.Tasks.TaskStatus;
import kz.bitlab.trelloCom.repositories.CategoryRep;
import kz.bitlab.trelloCom.repositories.FolderRep;
import kz.bitlab.trelloCom.repositories.TaskRep;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/trello"})
public class HomeController {
    private final FolderRep folderRep;
    private final TaskRep taskRep;
    private final CategoryRep categoryRep;

    @GetMapping({"/"})
    public String getIndex(Model model) {
        model.addAttribute("folders", this.folderRep.findAll());
        return "index";
    }

    @PostMapping({"/addFolder"})
    public String addFolder(@RequestParam(name = "folder") String folder) {
        Folders folder1 = new Folders();
        folder1.setName(folder);
        this.folderRep.save(folder1);
        return "redirect:/trello/";
    }

    @GetMapping({"/details/{id}"})
    public String details(@PathVariable(name = "id") Long id, Model model) {
        Folders folder = (Folders)this.folderRep.findById(id).orElseThrow();
        model.addAttribute("folder", folder);
        List<Tasks> tasks = this.taskRep.findByFolder(folder);
        model.addAttribute("tasks", tasks);
        List<Categories> categories = this.categoryRep.findAll();
        model.addAttribute("categories", categories);
        List<Categories> categoriesOfFolder = folder.getCategories();
        model.addAttribute("catOfFolder", categoriesOfFolder);
        return "details";
    }

    @PostMapping({"/addTask"})
    public String addTask(@RequestParam(name = "title") String title, @RequestParam(name = "description") String description, @RequestParam(name = "id") Long id) {
        Tasks task = new Tasks();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(TaskStatus.TODO);
        Folders folder = (Folders)this.folderRep.findById(id).orElseThrow();
        task.setFolder(folder);
        this.taskRep.save(task);
        return "redirect:/trello/details/" + id;
    }

    @PostMapping({"/addCategory"})
    public String addCategory(@RequestParam(name = "id") Long folder_id, @RequestParam(name = "category") Long cat_id, Model model) {
        Folders folder = (Folders)this.folderRep.findById(folder_id).orElseThrow();
        model.addAttribute("folder", folder);
        List<Categories> categories = null;
        if (folder != null) {
            if (folder.getCategories() != null) {
                categories = folder.getCategories();
            } else {
                categories = new ArrayList();
            }

            Categories category = (Categories)this.categoryRep.findById(cat_id).orElseThrow();
            ((List)categories).add(category);
            this.folderRep.save(folder);
        }

        return "redirect:/trello/details/" + folder_id;
    }

    @PostMapping({"/deleteCategory"})
    public String removeCategory(@RequestParam(name = "catOfFold_id") Long catOfFoldId, @RequestParam(name = "folder_id") Long folderId) {
        Folders folder = (Folders)this.folderRep.findById(folderId).orElseThrow();
        List<Categories> categories = null;
        if (folder != null) {
            if (folder.getCategories() != null) {
                categories = folder.getCategories();
            } else {
                categories = new ArrayList();
            }

            Categories category = (Categories)this.categoryRep.findById(catOfFoldId).orElseThrow();
            ((List)categories).remove(category);
            this.folderRep.save(folder);
        }

        return "redirect:/trello/details/" + folderId;
    }

    public HomeController(final FolderRep folderRep, final TaskRep taskRep, final CategoryRep categoryRep) {
        this.folderRep = folderRep;
        this.taskRep = taskRep;
        this.categoryRep = categoryRep;
    }
}