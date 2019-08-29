require_relative 'spec_helper'

describe 'Container' do
  describe file('/etc/alpine-release') do
    its(:content) { is_expected.to match(/3.9/) }
  end

  %i[dumb-init].each do |package|
    describe package(package) do
      it { is_expected.to be_installed }
    end
  end

  describe process('/usr/bin/dumb-init') do
    it { is_expected.to be_running }
  end

  describe 'java' do
    describe command('java -version') do
      its(:stderr) { is_expected.to match(/1.8.0/) }
    end

    describe process('java') do
      it { is_expected.to be_running }
      its(:args) { is_expected.to contain('app.jar') }
      its(:user) { is_expected.to eq('runner') }
    end

    describe 'listens to correct port' do
      it { wait_for(port(4003)).to be_listening.with('tcp') }
    end
  end

  describe file('app.jar') do
    it { is_expected.to be_file }
  end
end
