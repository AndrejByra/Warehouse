package JavaFx;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    public TextField textField;
    public TextField textField1;
    public TextField textField2;
    public TextField textField3;
    public Label label;

    public TableView<TableModel> table;
    public TableColumn<TableModel,String> col_product;
    public TableColumn<TableModel,String> col_buyer;
    public TableColumn<TableModel,Integer> col_pieces;
    public TableColumn<TableModel,String> col_country;

    private ObservableList<TableModel> list = FXCollections.observableArrayList();

    public void initialize(){
        try {
            ConnectionClass connection = new ConnectionClass();
            Connection conn = connection.getConnection();
            String getfromdb = "SELECT * FROM products";
            Statement statement =conn.createStatement();
            ResultSet resultSet = statement.executeQuery(getfromdb);
            list.clear();

            while(resultSet.next()){
                String product = resultSet.getString("product");
                String buyer = resultSet.getString("buyer");
                int pieces = resultSet.getInt("pieces");
                String country = resultSet.getString("country");
                System.out.println(product+buyer+pieces+country);
                list.add(new TableModel(product,buyer,pieces,country));
            }

        }catch (Exception e){

            e.printStackTrace();
            return;
        }
        col_product.setCellValueFactory(new PropertyValueFactory<>("product"));
        col_buyer.setCellValueFactory(new PropertyValueFactory<>("buyer"));
        col_pieces.setCellValueFactory(new PropertyValueFactory<>("pieces"));
        col_country.setCellValueFactory(new PropertyValueFactory<>("country"));
        table.setItems(list);


    }

    public void button(ActionEvent actionEvent) throws SQLException {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String product =textField.getText();
        String buyer = textField1.getText();
        int pieces =Integer.parseInt(textField2.getText());
        String country = textField3.getText();

        String sql = "INSERT INTO products (product,buyer,pieces,country) VALUES ('"+product+"','"+buyer+"','"+pieces+"','"+country+"')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);


        initialize();
    }



    public void carry(ActionEvent actionEvent) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String product =textField.getText();
        String buyer = textField1.getText();
        int pieces =Integer.parseInt(textField2.getText());
        String country = textField3.getText();
        Statement statement = connection.createStatement();

        String piecess = "SELECT `pieces` FROM `products` WHERE buyer like '"+buyer+"' and product like '"+product+"' and country like '"+country+"'";
        ResultSet rs = statement.executeQuery(piecess);
        if(rs.next()){
            if (rs.getInt("pieces")>pieces) {
                int pieces2 = rs.getInt("pieces") - pieces;
                String sql1 = "UPDATE products SET pieces = '" + pieces2 + "' where product like '" + product + "'";
                statement.executeUpdate(sql1);
            }else if(rs.getInt("pieces")<pieces){
                label.setText("");
                label.setText("We don't have so many pieces in stock");
            }else{
                String delete = "DELETE from products WHERE buyer like '"+buyer+"' and product like '"+product+"' and country like '"+country+"'";
                statement.executeUpdate(delete);
            }
        }else{
            label.setText("");
            label.setText("Product not found");
        }
        initialize();
    }


    public void update(ActionEvent actionEvent) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String product =textField.getText();
        String buyer = textField1.getText();
        int pieces =Integer.parseInt(textField2.getText());
        String country = textField3.getText();
        Statement statement = connection.createStatement();

        String piecesss = "SELECT `pieces` FROM `products` WHERE buyer like '"+buyer+"' and product like '"+product+"' and country like '"+country+"'";
        ResultSet rs = statement.executeQuery(piecesss);
        if(rs.next()){
            int pieces3 = rs.getInt("pieces")+pieces;
            String sql2 = "UPDATE products SET pieces = '"+pieces3+"' where product like '"+product+"'";
            statement.executeUpdate(sql2);

        }
        initialize();
    }
}

