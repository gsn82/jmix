package com.company.jmix.screen.main;

import com.company.jmix.app.TaskService;
import com.company.jmix.entity.Project;
import com.company.jmix.entity.ProjectTask;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiControllerUtils;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UiController("MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;
    @Autowired
    private CollectionContainer<Project> projectsDc;
    @Autowired
    private CollectionLoader<Project> projectsDl;
    @Autowired
    private CollectionLoader<ProjectTask> projectTasksDl;
    @Autowired
    private DateField<LocalDate> dateSelector;
    @Autowired
    private TextField<String> nameSelector;
    @Autowired
    private EntityComboBox<Project> projectSelector;
    @Autowired
    private Notifications notifications;
    @Autowired
    private TaskService taskService;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }

    @Subscribe("refreshAction")
    public void onRefreshAction(Action.ActionPerformedEvent event) {
        projectTasksDl.load();
        projectsDl.load();
    }

    @Subscribe("addTaskAction")
    public void onAddTaskAction(Action.ActionPerformedEvent event) {
        if (projectSelector.getValue() == null || nameSelector.getValue() == null || dateSelector.getValue() == null) {
            notifications.create(Notifications.NotificationType.ERROR).withCaption("Please fill all fields").show();
            projectSelector.focus();
            return;
        }
        ProjectTask newTask = taskService.creatNewTask(projectSelector.getValue(), nameSelector.getValue(), LocalDateTime.from(dateSelector.getValue()));
        projectsDc.replaceItem(newTask.getProject());
        projectsDl.load();

        projectSelector.setValue(null);
        nameSelector.setValue(null);
        dateSelector.setValue(null);
    }
}
