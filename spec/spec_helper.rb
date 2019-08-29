require 'serverspec'
require 'docker'
require 'rspec/wait'

set :backend, :docker
set :docker_image, 'cookery2-backend'

RSpec.configure do |config|
  config.wait_timeout = 60 # seconds
end
