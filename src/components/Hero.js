const HeroSection = () => {
  return (
    <section className="text-white bg-black/30 backdrop-blur-lg mx-3 my-9 p-12 rounded-xl shadow-lg shadow-blue-400/20 border border-blue-400/30 flex flex-col items-center space-y-6">
      <h1 className="text-4xl md:text-6xl font-bold text-center">
        Collaborate, Code, Create
      </h1>
      <p className="text-lg text-gray-300 max-w-xl text-center">
        Experience real-time coding with your team, across multiple languages.
        Seamlessly collaborate, debug, and build together like never before.
      </p>
      <div className="flex space-x-6 gap-y-4 flex-wrap">
        <button className="bg-blue-500 text-black px-6 py-3 rounded-full font-semibold hover:bg-blue-400 transition-all duration-300">
          Get Started
        </button>
        <button className="bg-white text-black px-6 py-3 rounded-full font-semibold hover:bg-gray-300 transition-all duration-300">
          Learn More
        </button>
      </div>
    </section>
  );
};

export default HeroSection;
