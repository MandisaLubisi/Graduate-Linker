package minproj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import minproj.Graph.CostPathPair;
import minproj.Graph.Vertex;

public class GradMatchJobClient extends GridPane{
	
	private Graph<String> graph;
    private ArrayListQueue<String> graduates;
    private ArrayListQueue<String> employers;
    private final String[] emojiNames = {"😡", "😔", "😐", "😊","😍"};
    private final String[] feedbackMessages = {"Very Dissatisfied", "Somewhat Dissatisfied", "Neutral",
            "Somewhat Satisfied", "Very Satisfied"};


    // constructor
    public GradMatchJobClient(Stage arg0){
        arg0.setTitle("Job Matching App");
        
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-border-color: #4B0082; -fx-border-width: 5;");
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Create the UI
        Label titleLabel = new Label("Graduate Linker");
        titleLabel.setStyle("-fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-background-color: #4B0082; -fx-padding: 20;");
        GridPane.setColumnSpan(titleLabel, 4);

        // Full Name
        Label nameLabel = new Label("Full Name:");
        nameLabel.setFont(Font.font("Arial", 14));
        TextField nameTextField = new TextField();
        nameTextField.setStyle("-fx-prompt-text-fill: #A0A0A0;");
        gridPane.add(titleLabel, 0, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameTextField, 1, 1);
        
        // Email
        Label emailLabel = new Label("Email Address:");
        emailLabel.setFont(Font.font("Arial", 14));
        TextField emailTextField = new TextField();
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailTextField, 1, 2);

        // ID Number
        Label idLabel = new Label("ID Number:");
        idLabel.setFont(Font.font("Arial", 14));
        TextField idTextField = new TextField();
        idTextField.setStyle("-fx-prompt-text-fill: #A0A0A0;");
        gridPane.add(idLabel, 0, 3);
        gridPane.add(idTextField,1,3);

        // Institution of Study
        Label institutionLabel = new Label("Institution of Study:");
        institutionLabel.setFont(Font.font("Arial", 14));
        TextField institutionTextField = new TextField();
        institutionTextField.setStyle("-fx-prompt-text-fill: #A0A0A0;");
        gridPane.add(institutionLabel, 0, 4);
        gridPane.add(institutionTextField, 1, 4);

        // Years of Experience
        Label experienceLabel = new Label("Years of Experience:");
        experienceLabel.setFont(Font.font("Arial", 14));
        ComboBox<String> experienceComboBox = new ComboBox<>();
        experienceComboBox.setStyle("-fx-prompt-text-fill: #A0A0A0;");
        experienceComboBox.getItems().addAll("0 years","1 years","2 years","3 or more years");
        experienceComboBox.getSelectionModel().selectFirst();
        gridPane.add(experienceLabel, 0, 5);
        gridPane.add(experienceComboBox, 1, 5);

        // Skills
        Label skillsLabel = new Label("Skills:");
        skillsLabel.setFont(Font.font("Arial", 14));
        ComboBox<String> skillsComboBox = new ComboBox<>();
        skillsComboBox.setStyle("-fx-prompt-text-fill: #A0A0A0;");
        skillsComboBox.getItems().addAll("BSc\\BCom in Computer Science\\BEng Computer Engineering-Junior Software Engineer role",
        		"BSc in Computer Science\\Engineering Qualification\\Mathematical Sciences-Graduate Data Scientist",
        		"BCom Accounting\\BAcc-Junior Chartered Accountant","MBCHB-Graduate Medical Doctor",
        		"BSc\\BEng\\BCom in any quantitative field-Graduate Quantitative Analyst",
        		"BCom Investment Management-Junior Financial Advisor");

        gridPane.add(skillsLabel, 0, 6);
        gridPane.add(skillsComboBox, 1, 6);

       
        //Match button
        Button matchButton = new Button("Match");
        matchButton.setStyle("-fx-font-size: 20; -fx-background-color: #4B0082; -fx-text-fill: #FFFFFF; -fx-padding: 10 30;");
        GridPane.setColumnSpan(matchButton, 4);
        Tooltip tooltip = new Tooltip("Click here to match an unemployed graduate with an employer");
        tooltip.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        matchButton.setTooltip(tooltip);
        GridPane.setConstraints(matchButton, 1, 7);
        gridPane.getChildren().add(matchButton);
        
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setSpacing(20);
        footer.setPadding(new Insets(20));
        Label infoLabel = new Label("Graduate Linker Job Matching Application ©2023");
        infoLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Hyperlink twitterLink = new Hyperlink("Twitter");
        twitterLink.setStyle("-fx-font-size: 14; -fx-text-fill: #4B0089;");
        Hyperlink facebookLink = new Hyperlink("Facebook");
        facebookLink.setStyle("-fx-font-size: 14; -fx-text-fill: #4B0081;");
        Hyperlink whatsappLink = new Hyperlink("Whatsapp");
        whatsappLink.setStyle("-fx-font-size: 14; -fx-text-fill: #4B0083;");
        Hyperlink instagramLink = new Hyperlink("Instagram");
        instagramLink.setStyle("-fx-font-size: 14; -fx-text-fill: #4B0082;");
        footer.getChildren().addAll(infoLabel, twitterLink, facebookLink,whatsappLink,instagramLink);
        
        GridPane mainGrid = new GridPane();
        mainGrid.add(gridPane,0,0);
        mainGrid.add(footer, 0, 1);
        mainGrid.setAlignment(Pos.CENTER);
        Image backgroundImage = new Image(getClass().getResource("job.jpg").toString());
        
     // Add some decorative shapes to the UI
        Circle circle = new Circle(10);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.web("#4B0082"));
        Rectangle rectangle = new Rectangle(15, 15);
        rectangle.setFill(Color.web("#4B0082"));
        rectangle.setStroke(Color.WHITE);
        Group shapesGroup = new Group(circle, rectangle);
        shapesGroup.setTranslateX(600);
        shapesGroup.setTranslateY(350);
        mainGrid.getChildren().add(shapesGroup);

        // Create a BackgroundImage and set its properties
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.ROUND ,BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        // Set the background of the GridPane to the newly created BackgroundImage
        mainGrid.setBackground(new Background(background));
       
        //Graduate Job Finding feedback Section
        BorderPane root = new BorderPane();

        // Top section with title and instructions
        Label title = new Label("Graduate Feedback");
        title.setFont(Font.font("Arial", 40));
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(20, 20, 10, 20));

        Label instructions = new Label("Please rate your experience with our service:");
        instructions.setFont(Font.font("Arial", 20));
        HBox instructionsBox = new HBox(instructions);
        instructionsBox.setAlignment(Pos.CENTER_LEFT);
        instructionsBox.setPadding(new Insets(0, 20, 20, 20));

        // Middle section with emojis and labels
        GridPane emojiGrid = new GridPane();
        emojiGrid.setAlignment(Pos.CENTER_RIGHT);
        emojiGrid.setPadding(new Insets(20));
        emojiGrid.setHgap(10);
        emojiGrid.setVgap(10);

        for (int i = 0; i < emojiNames.length; i++) {
            Label label = new Label(emojiNames[i]);
            label.setFont(Font.font("Arial", 48));
            Label feedback = new Label(feedbackMessages[i]);
            feedback.setFont(Font.font("Arial", 14));
            emojiGrid.add(label, i, 0);
            emojiGrid.add(feedback, i, 1);
        }

        // Bottom section with comment box and submit button
        Label commentLabel = new Label("Comments:");
        commentLabel.setFont(Font.font("Arial", 20));
        TextArea commentBox = new TextArea();
        commentBox.setWrapText(true);
        commentBox.setPrefRowCount(5);
        Button submitButton = new Button("Submit Feedback");
        submitButton.setFont(Font.font("Arial", 14));
        submitButton.setOnAction(e -> {
            // Handle submit button action here
            String feedbackValue = "";
            for (int i = 0; i < emojiNames.length; i++) {
                if (commentBox.getText().trim().isEmpty()) {
                    feedbackValue = "No comment provided";
                } else {
                    feedbackValue = commentBox.getText().trim();
                }
                System.out.println("Feedback value: " + feedbackValue);
            }
            commentBox.clear();
        });

        HBox commentBoxWrapper = new HBox(commentLabel, commentBox,submitButton);
        commentBoxWrapper.setSpacing(10);
        commentBoxWrapper.setPadding(new Insets(20, 20, 10, 20));
        
        root.setTop(titleBox);
        root.setLeft(instructionsBox);
        root.setRight(emojiGrid);
        root.setBottom(commentBoxWrapper);
        
        Image backgroundImg = new Image(getClass().getResource("review.jpg").toString());

        // Create a BackgroundImage and set its properties
        BackgroundImage backgroundFeed = new BackgroundImage(backgroundImg, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        // Set the background of the GridPane to the newly created BackgroundImage
        root.setBackground(new Background(backgroundFeed));
        
     // Define the mapping of required qualifications for each employer
        Map<String, String> employerQualifications = new HashMap<>();
        employerQualifications.put("BSc\\BCom in Computer Science\\BEng Computer Engineering-Junior Software Engineer role","Amazon");
        employerQualifications.put("BSc in Computer Science\\Engineering Qualification\\Mathematical Sciences-Graduate Data Scientist","Facebook");
        employerQualifications.put("BCom Accounting\\BAcc-Junior Chartered Accountant","IBM");
        employerQualifications.put("MBCHB-Graduate Medical Doctor","Huawei");
        employerQualifications.put("BSc\\BEng\\BCom in any quantitative field-Graduate Quantitative Analyst","Nokia");
        employerQualifications.put("BCom Investment Management-Junior Financial Advisor","Apple");
          
 // Find a match for the graduate using Dijkstra's shortest path algorithm
 matchButton.setOnAction(e -> {
	 
	 String graduate = nameTextField.getText();
	 String gradQualification = skillsComboBox.getValue();
	 System.out.println(graduate);
	 System.out.println(gradQualification);
	 String employer ="";
	 String matchQualification ="";
	 
	  // Create the Graph
     graph = new Graph<>(Graph.TYPE.UNDIRECTED);
     Vertex<String> employer1 = new Vertex<>("Amazon");
     Vertex<String> employer2 = new Vertex<>("Facebook");
     Vertex<String> employer3 = new Vertex<>("IBM");
     Vertex<String> employer4 = new Vertex<>("Huawei");
     Vertex<String> employer5 = new Vertex<>("Nokia");
     Vertex<String> employer6 = new Vertex<>("Apple");
     Vertex<String> unemployed = new Vertex<>(nameTextField.getText());
    
     // add employers as vertices to the Graph
     graph.getVertices().add(employer1);
     graph.getVertices().add(employer2);
     graph.getVertices().add(employer3);
     graph.getVertices().add(employer4);
     graph.getVertices().add(employer5);
     graph.getVertices().add(employer6);
     graph.getVertices().add(unemployed);
     
     // add graduates as edges to the Graph with cost of 1
     graph.getEdges().add(new Graph.Edge<>(1, employer1, unemployed));
     graph.getEdges().add(new Graph.Edge<>(1, employer2, unemployed));
     graph.getEdges().add(new Graph.Edge<>(1, employer3, unemployed));
     graph.getEdges().add(new Graph.Edge<>(1, employer4, unemployed));
     graph.getEdges().add(new Graph.Edge<>(1, employer5, unemployed));
     graph.getEdges().add(new Graph.Edge<>(1, employer6, unemployed));
    
     // Create the lists of employers and unemployed
     employers = new ArrayListQueue<>();
     employers.enqueue(employer1.getValue());
     employers.enqueue(employer2.getValue());
     employers.enqueue(employer3.getValue());
     employers.enqueue(employer4.getValue());
     employers.enqueue(employer5.getValue());
     employers.enqueue(employer6.getValue());
     
     graduates = new ArrayListQueue<>();
     graduates.enqueue(unemployed.getValue());
	 
	 //Check if the Grads qualifiacation matches the Skills qualification
	 if (gradQualification != null) {
          employer = employerQualifications.get(gradQualification);
          System.out.println(" = "+employer);
         if (employer != null) {
             // Match found, perform any additional actions here
            matchQualification = gradQualification;
            System.out.println("Match found: Key = " + matchQualification + ", Value = " + employer);
             
         }
         else {
        	 Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sorry, " + graduate + " does not have the required qualifications for this employer.");
             alert.showAndWait();
         }
     }
    String employers[] = {"Amazon","Facebook","IBM","Huawei","Nokia","Apple","Vodacom","MTN","University Of Johannesburg"};
 
    // Check if both employer and graduate exist in the graph
    Vertex<String> employerVertex = null;
    Vertex<String> graduateVertex = null;
    for (Vertex<String> vertex : graph.getVertices()) {
    	
        if (vertex.getValue().equals(employer)) {
            employerVertex = vertex;
            System.out.println("employerVertex = "+ vertex);
        } else if (vertex.getValue().equals(graduate)) {
            graduateVertex = vertex;
            System.out.println("graduateVertex = "+vertex);
        }
    	 
    }

    if (employerVertex == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Employer not found in the social graph.");
        alert.showAndWait();
        return;
    }

    if (graduateVertex == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Graduate not found in the social graph.");
        alert.showAndWait();
        return;
    }

    // Find the shortest path from the graduate to the employer
    CostPathPair<String> shortestPath = Dijkstra.getShortestPath(graph, graduateVertex, employerVertex);

    if (shortestPath == null) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sorry, we couldn't find a matching employer for your search criteria. " + graduate + " to " + employer);
        alert.showAndWait();
    } else {
        int cost = shortestPath.getCost();
        List<Vertex<String>> path = new ArrayList<>();
        for (Graph.Edge<String> edge : shortestPath.getPath()) {
            path.add(edge.getToVertex());
        }
        path.add(graduateVertex);
        Collections.reverse(path);

        String pathString = "";
        for (Vertex<String> vertex : path) {
            pathString += vertex.getValue() + " -> ";
        }
        pathString = pathString.substring(0, pathString.length() - 4);
        
        //Check if the Grads qualifiacation matches the Skills qualification again
   	     if (gradQualification != null) {
             employer = employerQualifications.get(gradQualification);
             System.out.println("employer = "+employer);
            if (employer != null) {
                // Match found, perform any additional actions here
               employer = gradQualification;
               matchQualification = gradQualification;
               System.out.println("Match found: Key = " + employer + ", Value = " + matchQualification);
               Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations! We found a matching employer for " + graduate + " to " + employer + " is (" + cost + ": " + pathString + ")");
               alert.showAndWait();
                
            }
            else {
           	 Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sorry, " + graduate + " does not have the required qualifications for this employer.");
                alert.showAndWait();
            }
        }

       
     Scene newScene = new Scene(root, 800, 600);

     // Get a reference to the current stage
      Stage currentStage = (Stage) arg0.getScene().getWindow();

     // Set the new scene as the root node of the current stage
      currentStage.setScene(newScene);

     // Show the new scene
     currentStage.show();
    }
});
       mainGrid.setAlignment(Pos.CENTER_LEFT);
       Scene scene = new Scene(mainGrid, 800, 600);
       scene.setFill(Color.LIGHTBLUE);
       arg0.setScene(scene);
       arg0.show();
    

    }


    
}
