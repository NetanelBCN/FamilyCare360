package demo.app.crud;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.app.boundaries.MiniAppCommandBoundary;
import demo.app.boundaries.UserBoundary;
import demo.app.boundaries.ObjectBoundary;
import demo.app.converters.CommandConverter;
import demo.app.converters.ObjectConverter;
import demo.app.converters.UserConverter;
import demo.app.logics.AdminLogic;

@Service
public class AdminCrudImplementation implements AdminLogic {

    private UserCrud userCrud;
    private UserConverter userConverter;
    private ObjectCrud objectCrud;
    private ObjectConverter objectConverter;
    private CommandCrud commandCrud;
    private CommandConverter commandConverter;

    private String springApplicationName;

    @Value("${spring.application.name:supperApp}")
    public void setSpringApplicationName(String springApplicationName) {
        this.springApplicationName = springApplicationName;
        System.err.println("The Spring Application name is: " + this.springApplicationName);
    }

    public AdminCrudImplementation(UserCrud userCrud, UserConverter userConverter, ObjectCrud objectCrud,
                                   ObjectConverter objectConverter, CommandCrud commandCrud, CommandConverter commandConverter) {
        this.userCrud = userCrud;
        this.userConverter = userConverter;
        this.objectCrud = objectCrud;
        this.objectConverter = objectConverter;
        this.commandCrud = commandCrud;
        this.commandConverter = commandConverter;
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        this.userCrud.deleteAll();
        System.err.println("All user entries Deleted");
    }

    @Override
    @Transactional
    public void deleteAllObjects() {
        this.objectCrud.deleteAll();
        System.err.println("All object entries Deleted");
    }

    @Override
    @Transactional
    public void deleteAllCommandsHistory() {
        this.commandCrud.deleteAll();
        System.err.println("All commands entries Deleted");
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBoundary> getAllUsers() {
        return this.userCrud.findAll()
                .stream()
                .peek(entity -> System.err.println("* " + entity))
                .map(this.userConverter::toBoundary)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MiniAppCommandBoundary> getAllCommands() {
        return this.commandCrud.findAll()
                .stream()
                .peek(entity -> System.err.println("* " + entity))
                .map(this.commandConverter::toBoundary)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObjectBoundary> getAllObjects() {
        return this.objectCrud.findAll()
                .stream()
                .peek(entity -> System.err.println("* " + entity))
                .map(this.objectConverter::toBoundary)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MiniAppCommandBoundary> getCommandsOfSpecificMiniApp(String miniAppName) {
        return Optional.empty();
    }
}