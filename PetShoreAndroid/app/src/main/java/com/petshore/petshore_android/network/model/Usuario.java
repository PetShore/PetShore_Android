package com.petshore.petshore_android.network.model;

import java.util.ArrayList;

public class Usuario {


    public String userId;
    public String username;
    public String nombreCompleto;
    public String direccionResidencia;
    public String password;
    public String email;
    public String barrio;
    public String numero;
    public ArrayList<Cliente> clientes;
    public ArrayList<Vendedor> vendedores;
    public ArrayList<Producto> productos;

    public Usuario() {
        this.clientes = new ArrayList<Cliente>();
        this.vendedores = new ArrayList<Vendedor>();
        this.productos = new ArrayList<Producto>();
    }

    public Usuario(String userId, String username, String nombreCompleto, String direccionResidencia, String password, String email, String barrio, String numero, ArrayList<Cliente> clientes, ArrayList<Vendedor> vendedores, ArrayList<Producto> productos) {
        this.userId = userId;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.direccionResidencia = direccionResidencia;
        this.password = password;
        this.email = email;
        this.barrio = barrio;
        this.numero = numero;
        this.clientes = clientes;
        this.vendedores = vendedores;
        this.productos = productos;
    }

    public Usuario(String username, String nombreCompleto, String password, String email,
            String barrio, String direccionResidencia, String numero) {
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.password = password;
        this.email = email;
        this.barrio = barrio;
        this.direccionResidencia = direccionResidencia;
        this.numero = numero;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(ArrayList<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProducto(Producto prod) {
        for (Producto prodTemp : getProductos()) {
            if (prodTemp.getCodigoId().equals(prod.getCodigoId())) {
                prodTemp.setNombre(prod.getNombre());
                prodTemp.setDescripcion(prod.getDescripcion());
                prodTemp.setPrecio(prod.getPrecio());
                break;
            }
        }
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public boolean addProductos(Producto producto) {
        return containId(producto);
    }

    public void changeValues(Usuario user) {
        this.password = user.password;
        this.direccionResidencia = user.direccionResidencia;
        this.numero = user.numero;
        this.email = user.email;
        this.barrio = user.barrio;
        this.clientes = user.clientes;
        this.vendedores = user.vendedores;
        this.productos = user.productos;
    }

    private boolean containId(Producto producto) {
        boolean repeat = false;
        if (!productos.isEmpty()) {
            for (Producto p : productos) {
                if (producto.getCodigoId().equals(p.getCodigoId())) {
                    repeat = true;
                    break;
                }
            }
        } else {
            productos.add(producto);
            repeat = true;
        }
        
        if(repeat == false){
            productos.add(producto);
        }

        return repeat;
    }
}
