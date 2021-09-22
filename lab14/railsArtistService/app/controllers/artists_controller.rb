class ArtistsController < ApplicationController

  skip_before_action :verify_authenticity_token

  def index
    artists = Artist.all

    render json: artists, :except => [:created_at, :updated_at], status: :ok
  end

  def show
    artist = Artist.find(params[:id])
    render json: artist, :except => [:created_at, :updated_at], status: :ok
  end

  def create

    artist = Artist.new(artist_params)

    if artist.save
      render json: {message: "Artist added successfully"}, status: :ok
    else
      render json: {message: "Artist not added"}, status: :unprocessable_entity
    end

  end

  def update

    artist = Artist.find(params[:id])
    if artist.update(artist_params)
      render json: {message: "Artist updated successfully"}, status: :ok
    else
      render json: {message: "Artist not found"}, status: :unprocessable_entity
    end

  end

  def destroy

    artist = Artist.find(params[:id])
    artist.destroy
    render json: {}, status: :ok

  end

  private

  def artist_params
    params.permit(:name, :description)
  end




end
