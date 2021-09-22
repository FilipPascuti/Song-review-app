FROM ruby:2.7.3

RUN apt-get update \
    && apt-get install -y --no-install-recommends postgresql-client \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /usr/src/app
COPY Gemfile* ./
RUN gem install bundler:2.1.4
RUN bundle install
COPY . .

CMD ["bash", "-c", "rails db:migrate && rails s -b 0.0.0.0 -p 3002"]
