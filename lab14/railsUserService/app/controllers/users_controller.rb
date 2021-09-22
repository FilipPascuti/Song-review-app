class UsersController < ApplicationController

  skip_before_action :verify_authenticity_token

  def index
    users = User.all

    render json: users, :except => [:created_at, :updated_at], status: :ok
  end

  def show
    user = User.find(params[:id])
    render json: user, :except => [:created_at, :updated_at], status: :ok
  end

  def create

    user = User.new(user_params)

    if user.save
      render json: {message: "User added successfully"}, status: :ok
    else
      render json: {message: "User not added"}, status: :unprocessable_entity
    end

  end

  def update

    user = User.find(params[:id])
    if user.update(user_params)
      render json: {message: "User updated successfully"}, status: :ok
    else
      render json: {message: "User not found"}, status: :unprocessable_entity
    end

  end

  def destroy

    user = User.find(params[:id])
    user.destroy
    render json: {}, status: :ok

  end

  private

  def user_params
    params.permit(:name)
  end

end
