package demo.app.crud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.app.boundaries.MiniAppCommandBoundary;
import demo.app.converters.CommandConverter;
import demo.app.converters.ObjectConverter;
import demo.app.entities.MiniAppCommandEntity;
import demo.app.entities.ObjectEntity;
import demo.app.entities.UserEntity;
import demo.app.enums.Role;
import demo.app.logics.CommandLogic;
import demo.app.objects.InputValidation;
import jakarta.annotation.PostConstruct;

@Service
public class CommandCrudImplementation implements CommandLogic {
	private CommandCrud commandCrud;
	private CommandConverter commandConverter;
	private String springApplicationName;
	private UserCrud userCrud;
	private ObjectCrud objectCrud;
	private ObjectConverter objectConverter;


	public CommandCrudImplementation(CommandCrud commandCrud, CommandConverter commandConverter, UserCrud userCrud,
			ObjectCrud objectCrud, ObjectConverter objectConverter) {
		this.commandCrud = commandCrud;
		this.commandConverter = commandConverter;
		this.userCrud = userCrud;
		this.objectCrud = objectCrud;
		this.objectConverter=objectConverter;
	}

	@Value("${spring.application.name:supperapp}")
	public void setup(String name) {
		this.springApplicationName = name;
		System.err.println("The Spring Application name is: " + this.springApplicationName);
	}

	@PostConstruct
	public void setupIsDone() {
		System.err.println("Command logic implementation is ready");
	}

	@Override
	@Transactional(readOnly = false)
	public List<Object> storeInDatabase(String miniAppName, MiniAppCommandBoundary commandBoundary) {
		validateCommandBoundary(commandBoundary);

		// Verify user permissions
		UserEntity user = userCrud
				.findById(commandBoundary.getInvokedBy().getUserId().getSuperapp() + "_"
						+ commandBoundary.getInvokedBy().getUserId().getEmail())
				.orElseThrow(() -> new MyForbiddenException("User not authorized"));
		if (!user.getRole().equals(Role.MINIAPP_USER)) {
			throw new MyForbiddenException("User not authorized");
		}

		// Verify target object existence and active status
		ObjectEntity targetObject = objectCrud
				.findById(commandBoundary.getTargetObject().getObjectId().getId() + "_"
						+ commandBoundary.getTargetObject().getObjectId().getSuperapp())
				.orElseThrow(() -> new MyBadRequestException("Target object not found in the database."));
		if (!targetObject.getActive()) {
			throw new MyBadRequestException("Target object is not active.");
		}
		commandBoundary.getCommandId().setId(UUID.randomUUID().toString());
		commandBoundary.getCommandId().setSuperapp(springApplicationName);
		commandBoundary.getCommandId().setMiniapp(miniAppName);
		commandBoundary.setInvocationTimeStamp(new Date());
		commandBoundary.getTargetObject().getObjectId().setSuperapp(springApplicationName);
		commandBoundary.getInvokedBy().getUserId().setSuperapp(springApplicationName);
		MiniAppCommandEntity entity = this.commandConverter.toEntity(commandBoundary);
		entity = this.commandCrud.save(entity);
		List<Object> boundaries = new ArrayList<>();
		boundaries.add(this.commandConverter.toBoundary(entity));
		
		
		String commandString = commandBoundary.getCommand();
		List<Object> result = handleCommand(entity);

		System.err.println("The command " + commandString + " is invoked successfully");
		return result;
	}

	private List<Object> handleCommand(MiniAppCommandEntity commandEntity) {
		String command = commandEntity.getCommand();
		List<ObjectEntity> entities;
		List<Object> rv = new ArrayList<>();

		switch (command) {
			case "GetAllObjectsByCreatedByAndActive":
				entities=objectCrud.findAllByCreatedByAndActiveTrue(commandEntity.getInvokedBy(), 
						PageRequest.of(0, 5, Direction.ASC, "objectID"));
				for (ObjectEntity entity : entities) 
					rv.add(this.objectConverter.toBoundary(entity));
				return rv;
				
			case "GetAllObjectsByCreatedByAndTypeAndAliasAndActive":
				entities=objectCrud.findAllByCreatedByAndActiveTrue(commandEntity.getInvokedBy(), 
						PageRequest.of(0, 5, Direction.ASC, "objectID"));	
				for (ObjectEntity entity : entities) 
					rv.add(this.objectConverter.toBoundary(entity));
				return rv;
				
			case "GetAllObjectsByTypeAndLocation":
				entities=objectCrud.findAllByCreatedByAndActiveTrue(commandEntity.getInvokedBy(), 
						PageRequest.of(0, 5, Direction.ASC, "objectID"));	
				for (ObjectEntity entity : entities) 
					rv.add(this.objectConverter.toBoundary(entity));
				return rv;
				
				
		}
		return null;
	}

	private void validateCommandBoundary(MiniAppCommandBoundary commandBoundary) {
		if (commandBoundary == null) {
			throw new MyBadRequestException("CommandBoundary cannot be null.");
		}
		if (commandBoundary.getCommand() == null || commandBoundary.getCommand().trim().isEmpty()) {
			throw new MyBadRequestException("Command cannot be null or empty.");
		}
		if (commandBoundary.getTargetObject() == null || commandBoundary.getTargetObject().getObjectId() == null
				|| commandBoundary.getTargetObject().getObjectId().getId() == null) {
			throw new MyBadRequestException("Target object cannot be null or empty.");
		}
		if (commandBoundary.getInvokedBy() == null) {
			throw new MyBadRequestException("Invoke by cannot be null.");
		}
		if (commandBoundary.getInvokedBy() == null
				|| !InputValidation.isValidEmail(commandBoundary.getInvokedBy().getUserId().getEmail())) {
			throw new MyBadRequestException("You must enter valid email.");
		}

	}
}
